<template>
    <div class="song-list-album">
        <div class="album-slide">
            <div class="album-img">
                <img :src="attachImageUrl(tempList.pic)"/>
            </div>
            <div class="album-info">
                <h2>简介</h2>
                <span>
                    {{tempList.introduction}}
                </span>
            </div>
        </div>
        <div class="album-content">
            <div class="album-title">
                <p>{{tempList.title}}</p>
            </div>
            <div class="album-score">
                <div>
                    <h3>歌单评分</h3>
                    <div>
                        <el-rate v-model="average" disabled></el-rate>
                    </div>
                </div>
                <span>
                    {{average}}
                </span>
                <div>
                    <h3>评价</h3>
                    <div @click="setRank">
                        <el-rate v-model="rank" allow-half show-text></el-rate>
                    </div>
                </div>
            </div>
            <div class="songs-body">
                <album-content :songList="listOfSongs">
                    <template slot="title">歌单</template>
                </album-content>
                <comment :playId="songListId" :type="1"></comment>
            </div>
        </div>
    </div>
</template>

<script>
import {mapGetters} from 'vuex';
import {mixin} from '../mixins';
import {listSongDetail,selectBySongId,setRank,getAvg} from '../api/index';
import AlbumContent from "../components/AlbumContent"
import Comment from '../components/Comment'
export default {
    name: 'song-list-album',
    mixins: [mixin],
    components: {
        AlbumContent,
        Comment,
    },
    data(){
        return{
            songLists: [],
            songListId: '',
            average: 0,
            rank: 0
        }
    },
    computed:{
        ...mapGetters([
            'listOfSongs',
            'tempList',
            'loginIn',
            'userId'
        ])
    },
    created(){
        this.songListId = this.$route.params.id;
        this.getSongId();
        this.getRank(this.songListId);
    },
    methods:{
        getSongId(){
            let _this = this;
            listSongDetail(this.songListId)
                .then(response => {
                    for(let item of response){
                        _this.getSongList(item.songId);
                    }
                    _this.$store.commit('setListOfSongs',this.songLists);
                })
                .catch(error => {
                    console.log(error);
                })
        },
        getSongList(id){
            selectBySongId(id)
                .then(response => {
                    this.songLists.push(response);
                })
                .catch(error => {
                    console.log(error)
                })
        },
        getRank(id){
            getAvg(id)
                .then(response => {
                    this.average = response/2;
                })
                .catch(error => {
                    console.log(error)
                })
        },
        setRank(){
            if(this.loginIn){
                let params = new URLSearchParams();
                params.append('songListId',this.songListId);
                params.append('consumerId',this.userId);
                params.append('score',this.rank*2);
                setRank(params)
                    .then(response => {
                        if(response.code == 1){
                            this.notify('评分成功','success');
                            this.getRank(this.songListId);
                        }else{
                            this.notify('您已经评价过了','error');
                        }
                    })
                    .catch(error => {
                        this.notify('评分失败','error')
                    })
            }else{
                this.rank = null;
                this.notify('请先登录','warning');
            }

        }
    }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/song-list-album.scss';
</style>