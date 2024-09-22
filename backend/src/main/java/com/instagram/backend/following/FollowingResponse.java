package com.instagram.backend.following;

import com.instagram.backend.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FollowingResponse {
    User LoggedInUser;
    User CurrentPageUser;
}
