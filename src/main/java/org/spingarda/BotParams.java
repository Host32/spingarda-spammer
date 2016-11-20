package org.spingarda;

/**
 * Created by Ivan on 19/11/2016.
 */
public class BotParams {
    private String page;

    private Long delay;

    private Integer postsAmount;

    private Integer commentsAmount;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Long getDelay() {
        return delay;
    }

    public void setDelay(Long delay) {
        this.delay = delay;
    }

    public Integer getPostsAmount() {
        return postsAmount;
    }

    public void setPostsAmount(Integer postsAmount) {
        this.postsAmount = postsAmount;
    }

    public Integer getCommentsAmount() {
        return commentsAmount;
    }

    public void setCommentsAmount(Integer commentsAmount) {
        this.commentsAmount = commentsAmount;
    }

    @Override
    public String toString() {
        return "BotParams{" +
                "page='" + page + '\'' +
                ", delay=" + delay +
                ", postsAmount=" + postsAmount +
                ", commentsAmount=" + commentsAmount +
                '}';
    }
}
