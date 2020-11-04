<template>
    <div class="baiduMap-constainer">
      <el-breadcrumb separator-class="el-icon-arrow-right" style="padding-left: 10px; padding-bottom: 10px; font-size: 12px">
        <el-breadcrumb-item :to="{ path: '/welcome' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>其他管理</el-breadcrumb-item>
        <el-breadcrumb-item>百度地图</el-breadcrumb-item>
      </el-breadcrumb>
      <el-card>
        <baidu-map :center="center" :zoom="zoom" @ready="handler" style="height:800px" @click="getClickInfo" :scroll-wheel-zoom='true'>
        </baidu-map>
      </el-card>
    </div>
</template>

<script>
    export default {
      name: "TestBaiDu",
      data() {
        return {
          center: {lng: 109.45744048529967, lat: 36.49771311230842},
          zoom: 13
        }
      },
      methods: {
        handler({BMap, map}) {
          let point = new BMap.Point(109.49926175379778, 36.60449676862417)
          map.centerAndZoom(point, 13)
          let marker = new BMap.Marker(point) // 创建标注
          map.addOverlay(marker) // 将标注添加到地图中
          let circle = new BMap.Circle(point, 6, {
            strokeColor: 'Red',
            strokeWeight: 6,
            strokeOpacity: 1,
            Color: 'Red',
            fillColor: '#f03'
          })
          map.addOverlay(circle)
        },
        getClickInfo(e) {
          this.center.lng = e.point.lng
          this.center.lat = e.point.lat
        }
      }
    }
</script>

<style scoped lang="less">
  .baiduMap-constainer{
    padding: 0;
    margin: 0;
    width: 100%;
    height: 100%;
  }
</style>
