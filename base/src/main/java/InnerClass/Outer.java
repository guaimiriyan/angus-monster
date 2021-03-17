package InnerClass;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName Outer.java
 * @Description 10.1创建内部类_内部类的简单使用
 * @createTime 2021年03月15日 11:03:00
 */
public class Outer {

    private String outName;

    public Outer(String outName) {
        this.outName = outName;
    }

    public String getOutName() {
        return outName;
    }

    public void setOutName(String outName) {
        this.outName = outName;
    }

    class Inner{
        private String innerName;

        public Inner(String innerName) {
            this.innerName = innerName;
        }

        public String getInnerName() {
            return innerName;
        }

        /**
         * 10.2 内部类拥有外部类中所有的权限
         */
        public String getOutField(){
            return outName;
        }

        public Outer getOuter(){
            return Outer.this;
        }

        public void setInnerName(String innerName) {
            this.innerName = innerName;
        }
    }

    /**
     * 在外部类中建立方法获取内部类
     */

    public Inner getInner(){
        return new Inner("我是内部类！");
    }

    public static void main(String[] args) {
        final Outer outer = new Outer("我是外部类llllll");
        final Inner inner = outer.getInner();
        final String outField = inner.getOutField();
        System.out.println(outField);
        /**
         * 10.3 因为每个内部类会持有一个外部类的引用
         * 使用外部类对象.new就可以告知内部对象
         */
        final Inner inner2 = outer.new Inner("我是内部类A！");
        System.out.println(inner2.getOutField());
        System.out.println(inner2.getOuter().getOutName());
        /**
         * 10.1如果在外部类的非静态方法的任意位置创建内部类
         */
        Outer.Inner inner1 = outer.getInner();
    }

}
