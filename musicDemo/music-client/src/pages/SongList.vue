<template>
    <div class="song-list">
        <ul class="song-list-header">
            <li v-for="(item,index) in songStyle" :key="index" @click="handleChangeView(item.name)"
                :class="{active: item.name == activeName}">
                {{item.name}}
            </li>
        </ul>
        <div>
            <content-list :contentList="data"></content-list>
            <div class="pagination">
                <el-pagination @current-change="hanleCurrenetChange" background layout="total,prev,pager,next"
                    :current-page="currentPage" :page-size="pageSize" :total="albumDatas.length">
                </el-pagination>
            </div>
        </div>
    </div>
</template>

<script>
import {mixin} from '../mixins'
import {getAllSongList,selectByStyle} from '../api/index'
import ContentList from '../components/ContentList'
import {songStyle} from '../assets/data/songList'
export default {
    mixins: [mixin],
    components: { 
      ContentList 
      },
    name: 'song-list',
    data(){
        return{
            albumDatas: [],
            pageSize: 15,
            currentPage: 1,
            songStyle: [],
            activeName: '全部歌单'
        }
    },
    computed:{
        data(){
            return this.albumDatas.slice((this.currentPage - 1)*this.pageSize,this.currentPage*this.pageSize);
        }
    },
    mounted(){
        this.songStyle = songStyle;
        this.getSearchList();
    },
    methods:{
        getSearchList(){
                getAllSongList()
                    .then(response => {
                        this.currentPage = 1;
                        this.albumDatas = response;
                    })
        },
        hanleCurrenetChange(val){
            this.currentPage = val;
        },
        handleChangeView(name){
            this.activeName = name;
            this.albumDatas = [];
            if(name == '全部歌单'){
                this.getSearchList();
            }else{
                this.selBystyle(name);
            }
        },
        selBystyle(style){
            selectByStyle(style)
                .then(response => {
                    this.currentPage = 1;
                    this.albumDatas = response;
                })
        }
    }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/song-list.scss';
</style>