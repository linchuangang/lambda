import com.alibaba.fastjson.JSON;
import com.lin.model.Student;
import org.junit.Before;
import org.junit.Test;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by Administrator on 2018/7/30.
 * https://www.ibm.com/developerworks/cn/java/j-lo-java8streamapi/
 */
public class LambdaTest {

    List<Student> studentList;

    @Before
    public void init() {
        studentList = new ArrayList<Student>() {
            {
                add(new Student(1, "stu1", 100.0));
                add(new Student(2, "stu2", 97.0));
                add(new Student(3, "stu3", 93.0));
                add(new Student(4, "stu4", 95.0));
                add(new Student(5, "stu1", 100.0));
                add(new Student(6, "stu2", 90.0));
                add(new Student(7, "stu3", 83.0));
                add(new Student(8, "stu4", 95.0));
            }
        };
    }

    //普通排序
    @Test
    public void test1() {
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
        //排序
        Collections.sort(studentList, (s1, s2) -> Double.compare(s1.getScore(), s2.getScore()));
        System.out.println(JSON.toJSONString(studentList));

        //过滤
        List<Student> list = studentList.stream().filter(s -> s.getName() == "stu1").collect(Collectors.toList());
        System.out.println(JSON.toJSONString(list));

        //方法引用
        List<String> list1 = Arrays.asList(new String[]{"Aid", "BId", "DDD", "DDD"});
        List<String> lowList = list1.stream().map(String::toLowerCase).collect(Collectors.toList());
        System.out.println(JSON.toJSONString(lowList));

        //过滤重复
        List<String> list2 = list1.stream().distinct().collect(Collectors.toList());
        System.out.println(JSON.toJSONString(list2));

        //计算
        double score = studentList.stream().mapToDouble(Student::getScore).sum();
        System.out.println(score);

        //flatMap
        Stream<List<Integer>> inputStream = Stream.of(
                Arrays.asList(1),
                Arrays.asList(2, 3),
                Arrays.asList(4, 5, 6)
        );
        Stream<Integer> outputStream = inputStream.flatMap((childList) -> childList.stream());
        System.out.println(outputStream.collect(Collectors.toList()));
        System.out.println(JSON.toJSONString(outputStream));

        //limit 返回 Stream 的前面 n 个元素
        List<String> list3 = list1.stream().limit(2).collect(Collectors.toList());
        System.out.println(JSON.toJSONString(list3));

        //skip  扔掉前 n 个元素
        List<String> list4 = list1.stream().skip(2).collect(Collectors.toList());
        System.out.println(JSON.toJSONString(list4));


    }


    @Test
    public void testStream() {
        List<Integer> ids = studentList.parallelStream()
                .filter(s -> s.getScore() > 93)
                .sorted(Comparator.comparing(Student::getScore).reversed())//排序
                .map(Student::getId)//取出id
                .collect(Collectors.toList());
        System.out.println(JSON.toJSONString(ids));

        Double sum = studentList.stream().filter(s -> s.getId() > 4).mapToDouble(s -> s.getScore()).sum();
        System.out.println(sum);
        Double sum2 = studentList.stream().filter(s -> s.getId() > 4).mapToDouble(Student::getScore).sum();
        System.out.println(sum2);

        IntStream.of(new int[]{1, 2, 3}).forEach(System.out::println);

        studentList.stream().forEach(s -> System.out.println(s.getName()));

        // 字符串连接，concat = "ABCD"
        String concat = Stream.of("A", "B", "C", "D").reduce("", String::concat);
        System.out.println(concat);
// 求最小值，minValue = -3.0
        double minValue = Stream.of(-1.5, 1.0, -3.0, -2.0).reduce(Double.MAX_VALUE, Double::min);
        System.out.println(minValue);
// 求和，sumValue = 10, 有起始值
        int sumValue = Stream.of(1, 2, 3, 4).reduce(0, Integer::sum);
        System.out.println(sumValue);
// 求和，sumValue = 10, 无起始值
        sumValue = Stream.of(1, 2, 3, 4).reduce(Integer::sum).get();
        System.out.println(sumValue);
// 过滤，字符串连接，concat = "ace"
        concat = Stream.of("a", "B", "c", "D", "e", "F").
                filter(x -> x.compareTo("Z") > 0).
                reduce("", String::concat);
        System.out.println(concat);

//        Stream 有三个 match 方法，从语义上说：
//        allMatch：Stream 中全部元素符合传入的 predicate，返回 true
//        anyMatch：Stream 中只要有一个元素符合传入的 predicate，返回 true
//        noneMatch：Stream 中没有一个元素符合传入的 predicate，返回 true
        Boolean isMatch = studentList.stream().allMatch(s -> s.getScore() > 10);
        System.out.println(isMatch);


    }


}
