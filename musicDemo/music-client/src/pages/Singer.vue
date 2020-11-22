<template>
    <div class="singer">
        <ul class="singer-header">
            <li v-for="(item,index) in singerStyle" :key="index" @click="handleChangeView(item)"
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
import { getAllSinger,getSingerBySex} from '../api/index'
import ContentList from '../components/ContentList'
import {singerStyle} from '../assets/data/singer'
export default {
    mixins: [mixin],
    components: { 
      ContentList 
      },
    name: 'singer',
    data(){
        return{
            albumDatas: [],
            pageSize: 15,
            currentPage: 1,
            singerStyle: [],
            activeName: '全部歌手'
        }
    },
    computed:{
        data(){
            return this.albumDatas.slice((this.currentPage - 1)*this.pageSize,this.currentPage*this.pageSize);
        }
    },
    mounted(){
        this.singerStyle = singerStyle;
        this.getSearchList();
    },
    methods:{
        getSearchList(){
                getAllSinger()
                    .then(response => {
                        this.currentPage = 1;
                        this.albumDatas = response;
                    })
        },
        hanleCurrenetChange(val){
            this.currentPage = val;
        },
        handleChangeView(item){
            this.activeName = item.name;
            this.albumDatas = [];
            if(item.name == '全部歌手'){
                this.getSearchList();
            }else{
                this.selBySex(item.type);
            }
        },
        selBySex(sex){
            getSingerBySex(sex)
                .then(response => {
                    this.currentPage = 1;
                    this.albumDatas = response;
                })
        }
    }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/singer.scss';
</style>