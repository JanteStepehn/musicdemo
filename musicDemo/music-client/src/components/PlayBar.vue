<template>
    <div class="play-bar" :class="{show:toggle}">
        <div @click="toggle=!toggle" class="item-up" :class="{turn:toggle}">  
            <svg class="icon">
                <use xlink:href="#icon-jiantou-xia-cuxiantiao"></use>
            </svg>
        </div>
        <div class="kongjian">
            <!-- 上一首 -->
            <div class="item" @click="prev">
                <svg class="icon">
                    <use xlink:href="#icon-ziyuanldpi"></use>
                </svg>
            </div>
            <!-- 播放 -->
            <div class="item" @click="togglePlay">
                <svg class="icon">
                    <use :xlink:href="playButtonUrl"></use>
                </svg>
            </div>
            <!-- 下一首 -->
            <div class="item" @click="next">
                <svg class="icon">
                    <use xlink:href="#icon-ziyuanldpi1"></use>
                </svg>
            </div>
            <!-- 歌曲图片 -->
            <div class="item-img" @click="toLyric">
                <img :src="picUrl"/>
            </div>
            <!-- 播放进度 -->
            <div class="playing-speed">
                <!-- 开始时间 -->
                <div class="current-time">{{nowTime}}</div>
                <div class="progress-box">
                    <div class="item-song-title">
                        <div>{{this.title}}</div>
                        <div>{{this.artist}}</div>
                    </div>
                    <div ref="progress" class="progress" @mousemove="mousemove">
                        <!-- 进度条 -->
                        <div ref="bg" class="bg" @click="updatemove">
                            <div ref="curProgress" class="cur-progress" :style="{width:curLength+'%'}"></div>
                        </div>
                        <!-- 进度点 -->
                        <div ref="idot" class="idot" :style="{left:curLength+'%'}" @mousedown="mousedown" @mouseup="mouseup"></div>
                    </div>
                </div>
                <!-- 播放结束时间 -->
                <div class="left-time">{{songTime}}</div>
                <!-- 音量 -->
                <div class="item item-volume">
                    <svg v-if="volume == 0" class="icon">
                        <use xlink:href="#icon-yinliangjingyinheix"></use>
                    </svg>
                    <svg v-else class="icon">
                        <use xlink:href="#icon-yinliang1"></use>
                    </svg>
                    <el-slider class="volume" v-model="volume" :vertical="true"></el-slider> 
                </div>
                <!-- 收藏 -->
                <div class="item" @click="collection">
                    <svg class="icon" :class="{active: isActive}">
                        <use xlink:href="#icon-xihuan-shi"></use>
                    </svg>
                </div>
                <!-- 下载 -->
                <div class="item" @click="download">
                    <svg class="icon">
                        <use xlink:href="#icon-xiazai"></use>
                    </svg>
                </div>
                <!-- 当前播放列表 -->
                 <div class="item" @click="changeAside">
                    <svg class="icon">
                        <use xlink:href="#icon-liebiao"></use>
                    </svg>
                </div>
            </div>
            
        </div>
    </div>
</template>

<script>
import {mapGetters} from 'vuex';
import { download,setCollect,existCollect } from '../api';
import {mixin} from '../mixins';
export default {
    name : 'play-bar',
    mixins: [mixin],
    data(){
        return {
            nowTime: '00:00',
            songTime: '00:00',
            curLength: 0,
            progressLength: 0,
            mouseStartX: 0,
            tag: false,
            volume: 50,
            toggle: true,
        }
    },
    computed: {
        ...mapGetters([
            'id',
            'url',
            'isPlay',
            'playButtonUrl',
            'picUrl',
            'title',
            'artist',
            'duration',
            'curTime',
            'showAside',
            'listIndex',
            'listOfSongs',
            'autoNext',
            'loginIn',
            'userId',
            'isActive',
        ])
    },
    watch:{
        isPlay(){
            if(this.isPlay){
                this.$store.commit('setPlayButtonUrl','#icon-zanting');
            }else{
                this.$store.commit('setPlayButtonUrl','#icon-bofang');
            }
        },
        curTime(){
            this.nowTime = this.formatSeconds(this.curTime);
            this.songTime = this.formatSeconds(this.duration);
            this.curLength = this.curTime / this.duration * 100;
        },
        volume(){
            this.$store.commit('setVolume',this.volume / 100);
        },
        autoNext(){
            this.next();
        }
    },
    created(){  
        this.$store.commit('setIsPlay',false);
        this.toggle = false;
    },
    mounted(){
        this.progressLength = this.$refs.progress.getBoundingClientRect().width; 
        document.querySelector('.item-volume').addEventListener('click',function(e){
            document.querySelector('.volume').classList.add('show-volume');
            e.stopPropagation();
        },false);
        document.querySelector('.volume').addEventListener('click',function(e){
            e.stopPropagation();
        },false);
        document.addEventListener('click',function(){
            document.querySelector('.volume').classList.remove('show-volume');
        },false);
    },
    methods: {
        //控制音乐播放暂停
        togglePlay(){
            if(this.listOfSongs.length > 0){
                if(this.isPlay){
                    this.$store.commit('setIsPlay',false);
                }else{
                    this.$store.commit('setIsPlay',true);
                }
            }
        },
        formatSeconds(val){
            let theTime = parseInt(val);
            let result = '';
            let minute = parseInt((theTime / 60) % 60);
            let second = parseInt(theTime % 60);
            if(minute > 0){
                if(minute < 10){
                    result = '0' + minute + ":";
                }else{
                    result = minute + ":";
                }
            }else{
                result = "00:";
            }
            if(second > 0){
                if(second < 10){
                    result += '0' + second ;
                    }else{
                        result += second;
                }
            }else{
                result += '00';
            }
            return result;
        },
        mousedown(e){
            this.mouseStartX = e.clientX;
            this.tag = true;
        },
        mouseup(){
            this.tag = false;
        },
        mousemove(e){
            if(!this.duration){
                return false;
            }
            if(this.tag){
                let movementX = e.clientX - this.mouseStartX;
                let curLength = this.$refs.curProgress.getBoundingClientRect().width;
                let newPercent = ((movementX+curLength)/this.progressLength)*100;
                if(newPercent>100){
                    newPercent = 100;
                }
                this.curLength = newPercent;
                this.mouseStartX = e.clientX;
                this.changeTime(newPercent);
            }
        },
        changeTime(percent){
            let newCurTime = (percent*0.01)*this.duration;
            this.$store.commit('setChangeTime',newCurTime);
        },
        updatemove(e){
            if(!this.tag){
                let curLength = this.$refs.bg.offsetLeft;
                let newPercent = ((e.clientX - curLength) / this.progressLength) * 100;
                if(newPercent > 100){
                    newPercent = 100;
                }else if(newPercent < 0){
                    newPercent = 0;
                }
                this.curLength = newPercent;
                this.changeTime(newPercent);
            }
        },
        changeAside(){
            this.$store.commit('setShowAside',true);
        },
        prev(){
            if(this.listIndex != -1 && this.listOfSongs.length > 1){
                if(this.listIndex > 0){
                    this.$store.commit('setListIndex',this.listIndex - 1);
                }else{
                    this.$store.commit('setListIndex',this.listOfSongs.length - 1);
                }
                this.toPlay(this.listOfSongs[this.listIndex].url);
            }
        },
        next(){
            if(this.listIndex != -1 && this.listOfSongs.length > 1){
                if(this.listIndex < this.listOfSongs.length - 1){
                    this.$store.commit('setListIndex',this.listIndex + 1);
                }else{
                    this.$store.commit('setListIndex',0);
                }
                this.toPlay(this.listOfSongs[this.listIndex].url);
            }
        },
        //播放
        toPlay(url){
            if(url && url != this.url){
                this.$store.commit('setId',this.listOfSongs[this.listIndex].id);
                this.$store.commit('setUrl',this.$store.state.configure.HOST+url);
                this.$store.commit('setPicUrl',this.$store.state.configure.HOST+this.listOfSongs[this.listIndex].pic);
                this.$store.commit('setTitle',this.replaceFName(this.listOfSongs[this.listIndex].name));
                this.$store.commit('setArtist',this.replaceLName(this.listOfSongs[this.listIndex].name));
                this.$store.commit('setLyric',this.parseLyric(this.listOfSongs[this.listIndex].lyric));
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
            }
        },
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
        },
        toLyric(){
            this.$router.push({path: '/lyric'})
        },
        download(){
            download(this.url)
            .then(response => {
                let content =response.data;
                let eleLink = document.createElement('a');
                eleLink.download = this.artist+'-'+this.title+'.mp3';
                eleLink.style.display = 'none';

                let blob = new Blob([content]);
                eleLink.href = URL.createObjectURL(blob);
                document.body.appendChild(eleLink);
                eleLink.click();
                document.body.removeChild(eleLink);
            })
            .catch(error => {
                console.log(error);
            })
        },
        collection(){
            if(this.loginIn){
                var params = new URLSearchParams();
                params.append('userId',this.userId);
                params.append('type',0);
                params.append('songId',this.id);
                setCollect(params)
                    .then(response => {
                        if(response.code == 1){
                            this.$store.commit('setIsActive',true);
                            this.notify('收藏成功','success');
                        }else{
                            this.notify('收藏失败','error')
                        }
                    })
            }else{
                this.notify('请先登录','warning');
            }
        }
    }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/play-bar.scss';
</style>