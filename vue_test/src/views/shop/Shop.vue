<template>
    <div class="shop_container">
      <el-breadcrumb separator-class="el-icon-arrow-right" style="padding-left: 10px; padding-bottom: 10px; font-size: 12px">
        <el-breadcrumb-item :to="{ path: '/welcome' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>业务管理</el-breadcrumb-item>
        <el-breadcrumb-item>商品管理</el-breadcrumb-item>
        <el-breadcrumb-item>商品搜索</el-breadcrumb-item>
      </el-breadcrumb>
      <el-card>
        <el-input placeholder="请输入商品名称" v-model="keyword" class="input_class" clearable>
          <el-button slot="append" icon="el-icon-search" @click="search"></el-button>
        </el-input>
        <el-button type="primary" @click="parse">同步入库</el-button>
        <el-row :span="24">
          <el-col :span="4" v-for="(shop,index) in goods" :key="index" style="margin: 10px 60px;">
            <div><el-image :src="shop.img" fit="fit" style="width: 100%; height: 100%"></el-image></div>
            <div :title="shop.title" v-html="shop.title" style="overflow: hidden;text-overflow: ellipsis;white-space: nowrap;text-align: center;"></div>
            <div style="display: flex; justify-content: space-between">
              <el-tag type="success">{{shop.price}}</el-tag>
              <span style="margin-top: 3px;">
                <button type="button" class="el-button el-button--text el-button--mini" @click="delShop(shop.id)" :id="shop.id">
                  <span>
                    <i class="el-icon-delete" ></i>删除
                  </span>
                </button>
              </span>
            </div>
          </el-col>
        </el-row>
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageIndex"
          :page-sizes="[4, 8, 16, 24]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
        </el-pagination>
      </el-card>
    </div>
</template>

<script>
    import {search, parse, del} from "../../api/shopApi";

    export default {
      name: "Shop",
      data() {
        return {
          keyword: '',
          pageIndex: 1,
          pageSize: 8,
          total: 0,
          goods: []
        };
      },
      methods: {
        delShop(id){
          if(document.querySelector('#'+id).classList.contains("isRemoved")){
            this.$message.warning("该商品已删除");
            return;
          }
          this.$confirm('删除商品, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.del(id);
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消删除'
            });
          });
        },
        async del(id){
          const {data} = await del(id);
          if(data.status){
            this.$message.success(data.message);
            document.querySelector('#'+id).classList.add("isRemoved");
          }else{
            this.$message.error(data.message);
          }
        },
        async parse(){
          if(this.keyword == ''){
            this.$message.error("商品名称不能为空");
            return false;
          }
          const {data} = await parse(this.keyword);
          if(data.status){
            this.$message.success("同步成功");
          }else{
            this.$message.error("同步失败");
          }
        },
        async search() {
          const {data} = await search(this.keyword, this.pageIndex, this.pageSize);
          if(data.status){
            this.goods = data.data.goods;
            this.total = data.data.total;
          }
        },
        handleSizeChange(val) {
          this.pageSize = val;
          this.search();
        },
        handleCurrentChange(val) {
          this.pageIndex = val;
          this.search();
        }
      },
      mounted() {
        this.search();
      }
    }
</script>

<style scoped lang="less">
  .shop_container {
    width: 100%;
    height: 100%;
    margin: 0;
    padding: 0;
  }
  .input_class {
    width: 600px;
    margin-bottom: 10px;
    margin-right: 20px;
  }
  .el-pagination {
    margin-top: 10px;
  }
  .row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
  }
</style>
