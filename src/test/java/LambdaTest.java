import com.alibaba.fastjson.JSON;
import com.lin.model.Student;
import org.junit.Before;
import org.junit.Test;


import java.util.*;
import java.util.function.Predicate;
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
                .sorted(Comparator.comparing(Student::getScore).reversed())//排序   reversed()降序  不加升序
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

        //groupingBy
        Map<Double, List<Student>> map = studentList.stream().collect(Collectors.groupingBy(Student::getScore));
        System.out.println(JSON.toJSONString(map));

        Map<Double, Long> map1 = studentList.stream().collect(Collectors.groupingBy(Student::getScore, Collectors.counting()));
        System.out.println(JSON.toJSONString(map1));

        //summarizing
        DoubleSummaryStatistics doubleSummaryStatistics = studentList.stream().collect(Collectors.summarizingDouble(Student::getScore));
        System.out.println(doubleSummaryStatistics);

        //partitioningBy
        Map<Boolean, List<Student>> map2 = studentList.stream().collect(Collectors.partitioningBy(s -> s.getScore() > 94));
        System.out.println(JSON.toJSONString(map2));

        //map reduce
        studentList.stream().map(student -> {
            return student.getScore() * student.getId();
        }).forEachOrdered(System.out::println);
    }

    @Test
    public void mapReduce() {
        double sum = studentList.stream().mapToDouble(s -> s.getScore()).sum();
        System.out.println(sum);
        DoubleSummaryStatistics doubleSummaryStatistics = studentList.stream().collect(Collectors.summarizingDouble(Student::getScore));
        System.out.println(doubleSummaryStatistics);
        Double sum2 = studentList.stream().collect(Collectors.summingDouble(Student::getScore));
        System.out.println(sum2);

        //map reduce
        List<Double> list = studentList.stream().map(Student::getScore).collect(Collectors.toList());
        System.out.println(list);
        Double sum3 = list.stream().map(x -> x + 1).reduce((sum4,x) -> sum4 + x).get();
        System.out.println(sum3);

       Double sum4= studentList.stream().map(student -> {
            return student.getScore()+1;
        }).reduce((s,x)->s+x).get();
        System.out.println(sum4);

    }


    @Test
    public void predicate(){
        List<String> languages = Arrays.asList("Java","Python","scala","Shell","R");
        System.out.println("Language starts with J: ");
        filterTest(languages,x -> x.startsWith("J"));
        System.out.println("\nLanguage ends with a: ");
        filterTest(languages,x -> x.endsWith("a"));
        System.out.println("\nAll languages: ");
        filterTest(languages,x -> true);
        System.out.println("\nNo languages: ");
        filterTest(languages,x -> false);
        System.out.println("\nLanguage length bigger three: ");
        filterTest(languages,x -> x.length() > 4);
    }
    //Stream API的过滤方法也接受一个Predicate，这意味着可以将我们定制的 filter() 方法替换成写在里面的内联代码
    public static void filterTest(List<String> languages, Predicate<String> condition) {
        languages.stream().filter(x -> condition.test(x)).forEach(x -> System.out.println(x + " "));
    }
}
