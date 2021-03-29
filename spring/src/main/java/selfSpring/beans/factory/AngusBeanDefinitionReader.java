package selfSpring.beans.factory;

import selfMvc.mvcframework.annotation.GPController;
import selfMvc.mvcframework.annotation.GPService;
import selfSpring.beans.config.AngusBeanDefinition;
import selfSpring.beans.support.AngusBeanDefinitionRegistry;
import selfSpring.beans.util.BeanNameUtil;

import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName AngusBeanDefinitionReader.java
 * @Description 主要是进行对配置文件的读取进行数据beandefinition的创建
 * @createTime 2021年03月28日 21:15:00
 */
public class AngusBeanDefinitionReader {

    public AngusBeanDefinitionRegistry beanFactory;

    Set<String> classes = new HashSet<>();
    public int loadBeanDefinitions(String[] paths,AngusBeanDefinitionRegistry beanFactory){
        this.beanFactory = beanFactory;
        int count = 0;
        for (String path : paths) {
            count += loadBeanDefinitions(path);
        }
        return count;
    }


    public int loadBeanDefinitions(String path){
        //1、扫描所有需要的class
        scanClass(path);
        //2、注册beandeafinition
        registerBeanDefinitions();
        return 0;
    }

    private void registerBeanDefinitions() {
        try {
            for (String aClass : classes) {
                Class<?> aClass1 = Class.forName(aClass);
                AngusBeanDefinition abd = new AngusBeanDefinition();
                abd.setBeanClass(aClass1);
                abd.setFactoryBeanName(BeanNameUtil.toLowerFirst(aClass1.getSimpleName()));
                this.beanFactory.registerBeanDefinition(abd.getFactoryBeanName(),abd);
            }
        }catch (Exception e){

        }

    }

    private void registerBeanDefinitions(String aClass) {

    }

    private void scanClass(String path) {
        //1、进行path获取
        URL classpath = getClass().getClassLoader().getResource(path.replaceAll("classpath", "").replaceAll("\\.", "/"));
        File oraginalFile = new File(classpath.getFile());
        final File[] files = oraginalFile.listFiles();
        for (File file : files) {
             if (file.isDirectory()){
                 scanClass(oraginalFile.getPath()+"/"+file.getName());
             }else {
                 if (!file.getName().contains(".class")) continue;
                 final String clazz = file.getName().replaceAll(".class", "");
                 if (checkClass(clazz)){
                     classes.add(clazz);
                 }
             }
        }

    }

    private boolean checkClass(String clazz) {
        try {
            Class<?> aClass = Class.forName(clazz);
            if (aClass.isAnnotationPresent(GPController.class)||aClass.isAnnotationPresent(GPService.class)) return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
        return false;
    }


}
