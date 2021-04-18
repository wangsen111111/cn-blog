package org.example.test;

public class Test {
    private static class P{
        protected int x = 3;
        public P(){
            System.out.println(x);
            s();
            System.out.println(x);
        }
        protected void s(){
            x = 4;
        }
    }
    private static class C extends P{
        protected int x = 1;
        public C(){
            //隐藏super();
            System.out.println(x);  //1
        }
        protected void s(){
            x = 6;
        }
    }

    public static void main(String[] args) {
        P c = new C();
        System.out.println(c.x);
        c.s();
        System.out.println(c.x);
        //结果：3 3 1 3 3
        //执行顺序：先会去调用父类构造方法，父类构造方法结束，再调用子类构造方法
        //如果是多态的使用，成员变量：编译看左边，运行也看左边
        //构造方法：先调用父类构造方法，再调用子类构造方法
        //成员方法：编译看左边运行看右边
        //无论是不是多态的用法，如果父类和子类中有两个变量名相同的成员变量
        //那么各是各的
        System.out.println();
        C c1 = new C();
        System.out.println(c1.x);
        c1.s();
        System.out.println(c1.x);
        //3 3 1 1 6
        //先调用父类的构造方法，父类构造方法结束，再调用子类构造方法
        //父类构造方法中如果携带子类重写的方法，那么是调用子类重写的方法
        //父类和子类中的成员变量都是各是各的

        //0 4 2
        Integer i1 = -129;
        Integer i2 = -129;
        int i3 = -129;
        System.out.println(i1==i2);
        System.out.println(i3==i1);
        System.out.println(i2==i3);

        String s = new StringBuilder("计算机").append("软件").toString();
        System.out.println(s.intern()=="计算机软件");    //true
        System.out.println(s.intern()==s);  //true
        System.out.println(s=="计算机软件"); //true
    }
}
