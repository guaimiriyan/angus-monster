package selfSpring.webmvc;

import java.util.Locale;
import java.io.File;

/**
 * Created by Tom on 2019/4/13.
 */
public class AngusViewResolver {

    private final String DEFAULT_TEMPLATE_SUFFX = ".html";

    private File templateRootDir;
//    private String viewName;

    public AngusViewResolver(String templateRoot) {
        String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();
        templateRootDir = new File(templateRootPath);
    }

    //GPView
    public Object resolveViewName(String viewName, Locale locale) throws Exception{
        if(null == viewName || "".equals(viewName.trim())){return null;}
        viewName = viewName.endsWith(DEFAULT_TEMPLATE_SUFFX) ? viewName : (viewName + DEFAULT_TEMPLATE_SUFFX);
        File templateFile = new File((templateRootDir.getPath() + "/" + viewName).replaceAll("/+","/"));
        return null;//new GPView(templateFile);
    }

//    public String getViewName() {
//        return viewName;
//    }
}
