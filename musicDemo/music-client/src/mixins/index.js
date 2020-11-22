import {selectByName,existCollect } from '../api/index'
import {mapGetters} from 'vuex'
export const mixin ={
    computed: {
        ...mapGetters([
            'id',
            'loginIn',
            'userId',
            'isActive',
        ])
    },
    methods: {
        //提示信息
        notify(title,type){
            this.$notify({
                title: title,
                type: type
            })
        },
        //获取图片地址
        attachImageUrl(srcUrl){
            return srcUrl? this.$store.state.configure.HOST+srcUrl : this.$store.state.configure.HOST+'/img/avatorPic/user.jpg'; 
        },
        //根据歌手名搜索
        getSong(){
            if(!this.$route.query.keywords){
                this.notify('您输入的内容为空','warning');
                this.$store.commit('setListOfSongs',[]);
            }else{
                selectByName(this.$route.query.keywords).then(response => {
                    if(!response.length){
                        this.$store.commit('setListOfSongs',[]);
                        this.notify('暂无数据','warning');
                    }else{
                        this.$store.commit('setListOfSongs',response);
                    }
                }).catch(error => {
                    console.log(error)
                })
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
        },
        attachBirth(val){
            return val.substr(0,10);
        }
    }
}