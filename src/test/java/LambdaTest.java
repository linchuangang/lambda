import com.alibaba.fastjson.JSON;
import com.lin.model.Student;
import org.junit.Test;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Administrator on 2018/7/30.
 */
public class LambdaTest {

    //普通
    @Test
    public void test1() {
        List<Student> studentList = new ArrayList<Student>() {
            {
                add(new Student("stu1", 100.0));
                add(new Student("stu2", 97.0));
                add(new Student("stu3", 93.0));
                add(new Student("stu4", 95.0));
            }
        };
        Collections.sort(studentList, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return Double.compare(o1.getScore(), o2.getScore());
            }
        });
        System.out.println(JSON.toJSONString(studentList));
    }

    //lambda写法
    @Test
    public void test2() {
        List<Student> studentList = new ArrayList<Student>() {
            {
                add(new Student("stu1", 100.0));
                add(new Student("stu2", 97.0));
                add(new Student("stu3", 93.0));
                add(new Student("stu4", 95.0));
            }
        };
        //排序
        Collections.sort(studentList, (s1, s2) -> Double.compare(s1.getScore(), s2.getScore()));
        System.out.println(JSON.toJSONString(studentList));

        //过滤
        List<Student> list = studentList.stream().filter(s -> s.getName() == "stu1").collect(Collectors.toList());
        System.out.println(JSON.toJSONString(list));

        //方法引用
        List<String> list1= Arrays.asList(new String[]{"Aid","BId","DDD","DDD"});
        List<String> lowList=list1.stream().map(String::toLowerCase).collect(Collectors.toList());
        System.out.println(JSON.toJSONString(lowList));

        //过滤重复
        List<String> list2=list1.stream().distinct().collect(Collectors.toList());
        System.out.println(JSON.toJSONString(list2));

        //计算
        double score =studentList.stream().mapToDouble(Student::getScore).sum();
        System.out.println(score);

        //
        Stream<List<Integer>> inputStream = Stream.of(
                Arrays.asList(1),
                Arrays.asList(2, 3),
                Arrays.asList(4, 5, 6)
        );
        Stream<Integer> outputStream = inputStream.flatMap((childList) -> childList.stream());
        System.out.println(outputStream.collect(Collectors.toList()));
        System.out.println(JSON.toJSONString(outputStream));

        //
        List<String> list3=list1.stream().limit(2).collect(Collectors.toList());
        System.out.println(JSON.toJSONString(list3));

    }


}
