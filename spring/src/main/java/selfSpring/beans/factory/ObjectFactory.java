package selfSpring.beans.factory;

@FunctionalInterface
public interface ObjectFactory<T> {

	/**
	 * 专门为单例创建的函数式接口
	 * @return
	 */
	T getObject();

}