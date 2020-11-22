<template>
    <transition name="slide-fade">
    <div class="the-aside" v-if="showAside">
        <h2 class="title">播放列表</h2>
        <ul class="menus">
            <li v-for="(item,index) in listOfSongs" :key="index" :class="{'is-play' : id==item.id}"
                @click="toPlay(item.id,item.url,item.pic,item.index,item.name,item.lyric)">
                {{replaceFName(item.name)}}
            </li>
        </ul>
    </div>
    </transition>
</template>

<script>
import {mapGetters} from 'vuex';
import { existCollect } from '../api';
export default {
    name: 'the-aside',
    computed: {
        ...mapGetters([
            'showAside',
            'listOfSongs',
            'id',
            'loginIn',
            'userId',
            'isActive',
        ])
    },
    mounted(){
        let _this = this;
        document.addEventListener('click',function(){
            _this.$store.commit('setShowAside',false);
        },true)
    },
    methods: {
        //歌手名
        replaceLName(str){
            let arr = str.split('-');
            return arr[0];
        },
        //歌名
        replaceFName(str){
            let arr = str.split('-');
            return arr[1];
        },
        //播放
        toPlay(id,url,pic,index,name,lyric){
            this.$store.commit('setId',id);
            this.$store.commit('setUrl',this.$store.state.configure.HOST+url);
            this.$store.commit('setPicUrl',this.$store.state.configure.HOST+pic);
            this.$store.commit('setListIndex',index);
            this.$store.commit('setTitle',this.replaceFName(name));
            this.$store.commit('setArtist',this.replaceLName(name));
            this.$store.commit('setLyric',this.parseLyric(lyric));
            this.$store.commit('setIsActive',false);
            if(this.loginIn){
                let params = new URLSearchParams();
                params.append('userId',this.userId);
                params.append('songId',this.id);
                existCollect(params)
                    .then(res => {
                        if(res){
                            this.$store.commit('setIsActive',true)
                        }
                    })
            }
        },
        parseLyric(text){
            let lines = text.split("\n");
            let pattern = /\[\d{2}:\d{2}.(\d{3}|\d{2})\]/g;
            let result = [];
            if(!(/\[.+\]/.test(text))){
                return [[0,text]];
            }
            while(!pattern.test(lines[0])){
                lines = lines.slice(1);
            }
            for(let item of lines){
                let time = item.match(pattern);
                let value = item.replace(pattern,'');
                for(let item of time){
                    let t = item.slice(1,-1).split(':');
                    if(value != ""){
                        result.push([parseInt(t[0],10)*60 + parseFloat(t[1]),value]);
                    }
                }
            }
            result.sort(function(a,b){
                return a[0] - b[0];
            });
            return result;
        }
    }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/the-aside.scss';
</style>