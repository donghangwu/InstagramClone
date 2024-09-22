package com.instagram.backend.follower;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.instagram.backend.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Follower {
    @Id
    @GeneratedValue
    private long id;

    private long followerId;
    private String followerEmail;
    private String followerName;
    private String followerProfilePicUrl;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @Override
    public String toString() {
        return "Follower{" +
                "id=" + id +
                ", followerId=" + followerId +
                ", followerEmail='" + followerEmail + '\'' +
                ", followerName='" + followerName + '\'' +
                ", followerProfilePicUrl='" + followerProfilePicUrl + '\'' +
                  +
                '}';
    }
}
