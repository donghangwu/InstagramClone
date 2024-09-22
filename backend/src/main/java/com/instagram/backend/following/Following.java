package com.instagram.backend.following;

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
public class Following {
    @Id
    @GeneratedValue
    private long id;

    private long followingId;
    private String followingEmail;
    private String followingName;
    private String followingProfilePicUrl;



    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @Override
    public String toString() {
        return "Following [id=" + id + "] [followingId=" + followingId + "] [followingEmail=" + followingEmail
                + "] [followingName=" + followingName + "] [followingProfilePicUrl=" + followingProfilePicUrl + "]";
    }
}
