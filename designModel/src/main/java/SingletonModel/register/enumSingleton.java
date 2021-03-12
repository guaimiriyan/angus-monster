package SingletonModel.register;

/**
 * @author angus
 * @version 1.0.0
 * @ClassName enumSingleton.java
 * @Description 梅举是最牛逼的单例方式
 * @createTime 2021年03月11日 17:39:00
 */
public enum enumSingleton {
    /**
     * 1、枚举如何防止反射进行单例的破坏
     *  if ((clazz.getModifiers() & Modifier.ENUM) != 0)
     *             throw new IllegalArgumentException("Cannot reflectively create enum objects");
     * 在constrctor中的newInstance中直接有如下
     *
     * 2、枚举如何防止序列化单例破坏
     *  if (cl != null) {
     *             try {
     *                 @SuppressWarnings("unchecked")
     *                 //最终获取的是转换，还是那个单例
     *                 Enum<?> en = Enum.valueOf((Class)cl, name);
     *                 result = en;
     *             } catch (IllegalArgumentException ex) {
     *                 throw (IOException) new InvalidObjectException(
     *                     "enum constant " + name + " does not exist in " +
     *                     cl).initCause(ex);
     *             }
     *             if (!unshared) {
     *                 handles.setObject(enumHandle, result);
     *             }
     *         }
     *
     *
     */
    INSTANCE;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
