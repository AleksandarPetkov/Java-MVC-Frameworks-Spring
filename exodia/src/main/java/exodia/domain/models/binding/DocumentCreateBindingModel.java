package exodia.domain.models.binding;

public class DocumentCreateBindingModel {

    private String title;

    private String content;

    public DocumentCreateBindingModel(){};

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
