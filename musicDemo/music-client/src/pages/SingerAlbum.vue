<template>
    <div class="singer-album">
        <div class="album-slide">
            <div class="singer-img">
                <img :src="attachImageUrl(singer.pic)"/>
            </div>
            <ul class="info">
                <li v-if="singer.sex==0||singer.sex==1">
                    {{attachSex(singer.sex)}}
                </li>
                <li>生日: {{attachBirth(singer.birth)}}</li>
                <li>地区: {{singer.location}}</li>
            </ul>
        </div>
        <div class="album-content">
            <div class="intro">
                <h2>{{singer.name}}</h2>
                <span>{{singer.introduction}}</span>
            </div>
            <div class="content">
                <album-content :songList="listOfSongs">
                    <template slot="title">歌单</template>
                </album-content>
            </div>
        </div>
    </div>
</template>

<script>
import {mapGetters} from 'vuex';
import {mixin} from '../mixins';
import {selectBySingerId} from '../api/index';
import AlbumContent from "../components/AlbumContent"
export default {
    name: 'singer-album',
    mixins: [mixin],
    components: {
        AlbumContent,
        Comment,
    },
    data(){
        return{
            singerId: '',
            singer: {},
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
        this.singerId = this.$route.params.id;
        this.singer = this.tempList;
        this.getSong();
    },
    methods:{
        getSong(){
            selectBySingerId(this.singerId)
                .then(response => {
                    this.$store.commit('setListOfSongs',response);
                })
                .catch(error => {
                    console.log(error)
                })
        },
        attachSex(val){
            if(val == 1){
                return '男';
            }else if(val == 0){
                return '女';
            }
            return '';
        },
        
    }
}
</script>
<style lang="scss" scoped>
@import '../assets/css/singer-album.scss';
</style>