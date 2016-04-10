package io.bgroup.topline.model;

import org.springframework.beans.factory.annotation.Autowired;

import java.sql.ResultSet;
import java.sql.SQLException;
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
        ResultSet resultSet = null;
        try {
            resultSet = dbMvc.getSelectResult(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (resultSet == null) return null;
        ArrayList<Post> postArrayList = null;
        try {
            while (resultSet.next()) {
                Post post = new Post();
                setPostFromResultSet(post, resultSet);
                if (postArrayList == null) postArrayList = new ArrayList<Post>();
                postArrayList.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return postArrayList;
    }

    private void setPostFromResultSet(Post post, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                String postId = resultSet.getString(DbModel.tablePost.id_post.toString());
                String postName = resultSet.getString(DbModel.tablePost.post_name.toString());
                if (postId!=null) post.setIdPost(postId);
                if (postName!=null) post.setPostName(postName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
