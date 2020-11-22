<template>
    <div class="my-music">
        <div class="album-slide">
            <div class="album-img">
                <img :src="attachImageUrl(avator)"/>
            </div>
            <ul class="album-info">
                <li>昵称:{{username}}</li>
                <li>性别:{{userSex}}</li>
                <li>生日:{{birth}}</li>
                <li>故乡:{{location}}</li>
            </ul>
        </div>
        <div class="album-content">
            <div class="album-title">
                个性签名：{{introduction}}
            </div>
            <div class="songs-body">
                <album-content :songList="collectList">
                    <template slot="title">我的收藏</template>
                </album-content>
            </div>
        </div>
    </div>
</template>

<script>
import {mapGetters} from 'vuex';
import {mixin} from '../mixins';
import { getUserById,getCollect,selectBySongId} from '../api/index';
import AlbumContent from "../components/AlbumContent"

export default {
    name: 'my-music',
    mixins: [mixin],
    components: {
        AlbumContent
    },
    data(){
        return{
            avator: '',
            username: '',
            userSex: '',
            birth: '',
            location: '',
            introduction: '',
            collection: [],
            collectList: [],
        }
    },
    computed:{
        ...mapGetters([
            'listOfSongs',
            'loginIn',
            'userId'
        ])
    },
    mounted(){
        this.getMsg(this.userId);
        this.getCollection(this.userId);
    },
    methods:{
        getMsg(userId){
            getUserById(userId)
                .then(res => {
                    this.avator = res.avator;
                    this.username = res.username;
                    if(res.sex == 0){
                        this.userSex = '女';
                    }else{
                        this.userSex = '男';
                    }
                    this.birth = this.attachBirth(res.birth);
                    this.location = res.location;
                    this.introduction = res.introduction;
                })
                .catch(error => {
                    console.log(error);
                })
        },
        getCollection(userId){
            getCollect(userId)
                .then(res => {
                    this.collection = res;
                    for(let item of res){
                        this.getSong(item.songId);
                    }
                })
                .catch(error => {
                    console.log(error);
                })

        },
        getSong(id){
            selectBySongId(id)
                .then(res => {
                    this.collectList.push(res)
                })
                .catch(error => {
                    console.log(error);
                })
        }
    }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/my-music.scss';
</style>