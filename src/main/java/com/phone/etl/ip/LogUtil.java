package com.phone.etl.ip;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

import java.io.IOException;
import java.util.Map;

public class LogUtil {



    public static Map<String,String> parseLog(String log){

        return null;
    }

public static class MyMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    Text word = new Text();
    IntWritable one = new IntWritable(1);

    @Override
    protected void map(LongWritable key, Text value,Context context)
            throws IOException, InterruptedException {
        //获取行数据
        String line = value.toString();
        //对数据进行拆分
        String []  words = line.split(" ");
        //循环数组
        for (String s : words) {
            word.set(s);
            context.write(word, one);

        }

    }
}

/**
 * 自定义reducer类
 * @author lyd
 *
 */
public static class MyReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    IntWritable sum = new IntWritable();

    @Override
    protected void reduce(Text key, Iterable<IntWritable> value,Context context)
            throws IOException, InterruptedException {
        //定义一个计数器
        int counter = 0;

        //循环奇数
        for (IntWritable i : value) {
            counter += i.get();
        }
        sum.set(counter);
        //reduce阶段的最终输出
        context.write(key, sum);

    }
}

    /**
     * job的主入口
     * @param args
     */
    public static void main(String[] args) {

        try {
            //获取配置对象
            Configuration conf = new Configuration();
            //创建job
            Job job = new Job(conf, "Logutil");
            //为job设置运行主类
            job.setJarByClass(LogUtil.class);

            //设置map阶段的属性
            job.setMapperClass(MyMapper.class);
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(IntWritable.class);
            FileInputFormat.addInputPath(job, new Path(args[0]));


            //设置reduce阶段的属性
            job.setReducerClass(MyReducer.class);
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(IntWritable.class);
            FileOutputFormat.setOutputPath(job, new Path(args[1]));

            //提交运行作业job 并打印信息
            int isok = job.waitForCompletion(true)?0:1;
            //退出job
            System.exit(isok);

        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

