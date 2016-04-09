package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by VSB on 08.04.2016.
 * ToplineWeb.2.5
 */
public class Post {
    @Autowired
    private DbModel dbMvc;

    private String idPost;
    private String postName;

    public String getIdPost() {
        return idPost;
    }

    private void setIdPost(String idPost) {
        this.idPost = idPost;
    }

    public String getPostName() {
        return postName;
    }

    private void setPostName(String postName) {
        this.postName = postName;
    }

    public ArrayList<Post> getPostList() {
        ArrayList<Post> postList = null;
        String sql = "select * from post";
        postList = getPostFromDbSelect(sql);
        return postList;
    }

    public Post getPost(String idPost) {
        ArrayList<Post> postArrayList = null;
        String sql = "select * from post where id_post='" + idPost + "'";
        postArrayList = getPostFromDbSelect(sql);
        if (postArrayList == null || postArrayList.size() == 0) return null;
        return postArrayList.get(0);
    }

    private ArrayList<Post> getPostFromDbSelect(String sql) {
        List<Map<String, Object>> postListFromDb = null;
        postListFromDb = dbMvc.getSelectResult(sql);
        if (postListFromDb == null) return null;
        ArrayList<Post> postArrayList = null;
        for (Map row : postListFromDb) {
            Post post = new Post();
            post.setIdPost((String) row.get("id_post").toString());
            post.setPostName((String) row.get("post_name").toString());
            if (postArrayList == null) postArrayList = new ArrayList<Post>();
            postArrayList.add(post);
        }
        return postArrayList;
    }
}
