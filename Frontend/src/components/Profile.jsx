import React, { useState } from 'react'
import gridIcon from '../img/gridIcon.jpg'
import { useEffect } from 'react'
import axios from 'axios'
import { useParams } from 'react-router-dom'
export default function Profile() {
    //user posts
    const [userPosts, setuserPosts] = useState([])
    //user info
    const [userinfo, setuserinfo] = useState('')
    //extract userid from req params
    const { userid } = useParams();

    useEffect(() => {
        var ignore = false;
        const cancelTokenGetMyPosts = axios.CancelToken.source();
        const fetchProfile = async () => {
            try {

                var result = await axios.get(`api/post/posts/${userid}`, {
                    headers: {
                        "Authorization": 'Bearer ' + localStorage.getItem('jwt')
                    }
                }, {
                    cancelToken: cancelTokenGetMyPosts.token
                })
                result = result.data;
                // console.log("user profile: ", result)
                if (!ignore) {
                    setuserinfo(result.user)
                    setuserPosts(result.posts)
                }
                // setuserinfo(result.user)
                // setuserPosts(result.posts)
                // console.log("profile userinfo: ", userinfo)
                // console.log("profile setuserPosts: ", userPosts)

            } catch (error) {
                console.log(error);
            }

        }
        fetchProfile();

        return () => {
            ignore = true;
            cancelTokenGetMyPosts.cancel();
        }
    }, [])//only fetch the data onces

    //follow a user
    const following = async () => {
        try {
            console.log("following userid: ", userid)
            var result = await axios.post('api/following/follow', {
                followId: userid
            }, {
                headers: {
                    'Authorization': 'Bearer ' + localStorage.getItem('jwt')
                }
            })
            //console.log("following result: ", result.data)
            result = result.data;

            result.currentPageUser.password = ""
            //console.log("following result: ", result.currentPageUser)
            //update the being followed user's state
            setuserinfo(result.currentPageUser);

            //update userinfo stored in localstorage 
            localStorage.setItem('userinfo', JSON.stringify(result.loggedInUser))
            window.location.reload();

        } catch (error) {

        }
    }
    //unfollow a user
    const unfollowing = async () => {
        try {
            var result = await axios.post('api/following/unfollow', {
                followId: userid
            }, {
                headers: {
                    'Authorization': 'Bearer ' + localStorage.getItem('jwt')
                }
            })
            result = result.data;
            //update the being unfollowed user's state
            setuserinfo(result.currentPageUser);
            //update userinfo stored in localstorage !!
            localStorage.setItem('userinfo', JSON.stringify(result.loggedInUser))
            window.location.reload();
        } catch (error) {

        }
    }

    //generate the follow/unfollow button
    const followBtn = () => {
        try {
            //setuserinfo();
            const followings = JSON.parse(localStorage.getItem("userinfo")).following;
            var find = false;

            // console.log("followings: ", followings)
            // console.log("userid: ", userid)
            followings.forEach(element => {
                if ((element.followingId) === Number(userid)) {
                    //console.log("elementid: ", typeof (element.followingId))
                    find = true;
                }
            });
            if (find) {
                return (
                    <button className="btn waves-effect waves-light red lighten-1 followbtn"
                        name="action" onClick={unfollowing} >
                        unfollow
                    </button>
                )
            }
            else {
                return (
                    <button className="btn waves-effect waves-light green lighten-1 followbtn"
                        name="action" onClick={following} >
                        follow
                    </button>
                )
            }

        } catch (err) {
            console.log(err)
        }
    }
    const followNum = (follows) => {
        if (follows) {
            return follows.length
        }
        else
            return 0;
    }

    return (

        <div>

            <div className="profilecard">
                <div className="card horizontal">
                    <div >
                        <img className='profileImg' alt='profile img' src={userinfo ? userinfo.img : 'loading..'} />
                    </div>
                    <div className="card-stacked">
                        <div className="card-content">
                            <h3>{userinfo.name}
                                {followBtn()}
                            </h3>
                            <h6>{userinfo.email}</h6>
                            <br />
                            <br />
                            <div className="profileStats">
                                <h6>{userPosts.length} posts</h6>
                                <h6>{followNum(userinfo.followers)} followers</h6>
                                <h6>{followNum(userinfo.followering)} following</h6>

                            </div>
                        </div>
                        <br />
                        <br />

                    </div>
                </div>
            </div>

            <div className='center postText'>
                <img style={{ width: '20px', marginRight: '10px' }} src={gridIcon} alt='grid img'></img>
                Gallery</div>
            <div className='userPosts'>
                {

                    userPosts.map(post => {
                        return (
                            <img className='post' src={post.picture} alt='user img' key={post.id} />
                        )
                    })
                }
            </div>

        </div>

    )
}
