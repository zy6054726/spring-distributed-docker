public class TestMapperUtil {

    public static void main(String[] args) {
        String s = new String("1");
        s.intern();
        String s2 = "1";
        System.out.println(s == s2);


//        String s1 = new String("1") + "1";
        String s1 = "11";
//        s1.intern();
        String s3 = "11";
        System.out.println(s1 == s3);
    }
}
