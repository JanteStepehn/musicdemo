<template>
  <div class="home">
    <swiper />
    <div class="section" v-for="(item,index) in songsList" :key="index">
      <div class="section-title">{{item.name}}</div>
        <content-list :contentList="item.list"></content-list>
    </div>
  </div>
</template>

<script>
import Swiper from '../components/Swiper';
import {getAllSinger,getAllSongList} from '../api/index'
import ContentList from '../components/ContentList.vue';
export default {
  name: 'home',
  components: {
    Swiper,
    ContentList,
  },
  data () {
    return {
      songsList: [
        {name:"歌单",list:[]},
        {name:"歌手",list:[]},
      ]
    }
  },
  created () {
    this.getSongList();
    this.getSinger();
  },
  methods: {
    getSongList(){
      getAllSongList().then(response =>{
        this.songsList[0].list = response.slice(0,10);
      }).catch(error => {
        console.log(error)
      })
    },
    getSinger(){
      getAllSinger().then(response =>{
        this.songsList[1].list = response.slice(0,10);
      }).catch(error => {
        console.log(error)
      })
    }
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/home.scss';
</style>
