<template>
     <div class="search-songs">
        <album-content :songList="listOfSongs"></album-content>
    </div>
</template>

<script>
import {mixin} from '../../mixins/index'
import {mapGetters} from 'vuex'
import AlbumContent from "../AlbumContent"
export default {
    name: 'search-songs',
    components: {
        AlbumContent
    },
    mixins: [mixin],
    computed: {
        ...mapGetters([
            'listOfSongs'
        ])
    },
    mounted() {
        this.getSong();
    }
}
</script>

<style lanng="scss" scoped>
@import '../../assets/css/search-songs.scss';
</style>