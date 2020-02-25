package veinthrough.taco.model.href;

public interface Href {
    String getHref();
    static String getIdFromHref(String href) {
        return href.substring(href.lastIndexOf("/") + 1);
    }
}
