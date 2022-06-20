package com.toy.chanygram.dto.user;

import com.toy.chanygram.domain.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {

    private boolean pageOwner;
    private boolean subscribeState;
    private int following;
    private int follower;
    private int imageCount;

    private Long userId;
    private String username;
    private String userAboutMe;
    private String userWebsite;
    private String userProfileImageUrl;
    private List<ImagesWithLikes> images = new ArrayList<>();

    public void setImages(List<Image> image) {
        image.forEach(
                i -> {
                    this.images.add(new ImagesWithLikes(i.getId(), i.getPostImageUrl(), i.getLikes().size()));
                }
        );
    }

    @Getter @Setter
    public class ImagesWithLikes {

        Long imageId;
        String postImageUrl;
        int likesCount;

        public ImagesWithLikes(Long imageId, String postImageUrl, int likesCount) {
            this.imageId = imageId;
            this.postImageUrl = postImageUrl;
            this.likesCount = likesCount;
        }
    }
}





