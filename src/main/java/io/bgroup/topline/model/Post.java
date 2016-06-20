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
    private DbJdbcModel dbMvc;

    private int idPost;
    private String postName;

    public int getIdPost() {
        return idPost;
    }

    private void setIdPost(int idPost) {
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
        postList = getPostFromDbSelect(sql, null);
        return postList;
    }

    public Post getPost(String idPost) {
        if (idPost == null) return null;
        ArrayList<Post> postArrayList = null;
        String sql = "select * from post where id_post=?";
        ArrayList<Object> args = new ArrayList<Object>();
        args.add(idPost);
        postArrayList = getPostFromDbSelect(sql, args);
        if (postArrayList == null || postArrayList.size() == 0) return null;
        return postArrayList.get(0);
    }

    public Post getPost(int idPost) {
        ArrayList<Post> postArrayList = null;
        String sql = "select * from post where id_post=?";
        ArrayList<Object> args = new ArrayList<Object>();
        args.add(idPost);
        postArrayList = getPostFromDbSelect(sql, args);
        if (postArrayList == null || postArrayList.size() == 0) return null;
        return postArrayList.get(0);
    }

    private ArrayList<Post> getPostFromDbSelect(String sql, ArrayList<Object> args) {
        List<Map<String, Object>> postListFromDb = null;
        postListFromDb = dbMvc.getSelectResult(sql, args);
        if (postListFromDb == null) return null;
        ArrayList<Post> postArrayList = null;
        for (Map row : postListFromDb) {
            Post post = new Post();
            post.setIdPost((Integer) row.get("id_post"));
            post.setPostName((String) row.get("post_name").toString());
            if (postArrayList == null) postArrayList = new ArrayList<Post>();
            postArrayList.add(post);
        }
        return postArrayList;
    }
}
