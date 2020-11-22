<template>
    <div>
        <div class="comment">
            <h2>评论</h2>
            <div class="comment-msg">
                <div class="comment-img">
                    <img :src="attachImageUrl(avator)"/>
                </div>
                <el-input class="comment-input" type="textarea" :rows="2" placeholder="请输入内容" v-model="textarea"/>
            </div>
            <el-button type="primary" class="sub-btn" @click="postComment">评论</el-button>
        </div>
        <p>精彩评论：共{{commentList.length}}条评论</p>
        <ul class="popular" v-for="(item,index) in commentList" :key="index">
            <li>
                <div class="popular-img">
                    <img :src="attachImageUrl(userPic[index])"/>
                </div>
                <div class="popular-msg">
                    <ul>
                        <li class="name">
                            {{userName[index]}}
                        </li>
                        <li class="time">
                            {{item.createTime}}
                        </li>
                        <li class="name">
                            {{item.content}}
                        </li>
                    </ul>
                </div>
                <div class="up" ref="up" @click="postUp(item.id,item.up,index)">
                    <svg class="icon">
                        <use xlink:href="#icon-zan"></use>
                    </svg>
                    {{item.up}}
                </div>
            </li>
        </ul>
    </div>
</template>

<script>
import {mapGetters} from 'vuex'
import {mixin} from '../mixins/index'
import {setComment,setLike,getAllComment,getUserById} from '../api/index';
export default {
    mixins: [mixin],
    name: 'comment',
    props: [
        'playId',
        'type',
    ],
    computed:{
        ...mapGetters([
            'id',
            'loginIn',
            'userId',
            'avator',
        ])
    },
    data(){
        return {
            textarea: '',
            commentList: [],
            userPic: [],
            userName: [],
        }
    },
    mounted(){
        this.getComment();
    },
    methods:{
        postComment(){
            if(this.loginIn){
                let params = new URLSearchParams();
                if(this.type == 0){
                    params.append('songId',this.playId);
                }else{
                    params.append('songListId',this.playId);
                }
                params.append('userId',this.userId);
                params.append('type',this.type);
                params.append('content',this.textarea);
                setComment(params)
                    .then(response => {
                        if(response.code == 1){
                            this.notify('评论成功','success');
                            this.textarea = '';
                            this.getComment();
                        }else{
                            this.notify('评论失败','error');
                        }
                    })
                    .catch(error => {
                        this.notify('评论失败','error')
                    })
            }else{
                this.notify('请先登录','warning');
            }
        },
        getComment(){
            getAllComment(this.type,this.playId)
                .then(response => {
                        this.commentList = response;
                        for(let item of response){
                            this.getUsers(item.userId);
                        }
                    })
                    .catch(error => {
                        this.notify('出错了','error')
                    })
        },
        getUsers(id){
            getUserById(id)
                .then(response => {
                        this.userPic.push(response.avator);
                        this.userName.push(response.username);
                    })
                    .catch(error => {
                        this.notify('出错了','error')
                    })
        },
        postUp(id,up,index){
            if(this.loginIn){
                let params = new URLSearchParams();
                params.append('id',id);
                params.append('up',up+1);
                setLike(params)
                    .then(response => {
                        if(response.code == 1){
                            this.$refs.up[index].children[0].style.color = '#2796cd';
                            this.getComment();
                        }else{
                            this.notify('点赞失败','error');
                        }
                    })
                    .catch(error => {
                        this.notify('点赞失败','error')
                    })
            }else{
                this.notify('请先登录','warning');
            }
        }
    }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/comment.scss';
</style>