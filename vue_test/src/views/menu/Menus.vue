<template>
    <div class="menu-container">
      <el-breadcrumb separator-class="el-icon-arrow-right" style="padding-left: 10px; padding-bottom: 10px; font-size: 12px">
        <el-breadcrumb-item :to="{ path: '/welcome' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>系统管理</el-breadcrumb-item>
        <el-breadcrumb-item>菜单权限</el-breadcrumb-item>
      </el-breadcrumb>
      <el-card class="users-box-card">
        <el-input
          placeholder="输入关键字进行过滤"
          v-model="filterText">
        </el-input>
        <p>菜单权限树</p>
        <el-tree
          class="filter-tree"
          :data="data"
          :props="defaultProps"
          show-checkbox
          default-expand-all
          :filter-node-method="filterNode"
          ref="tree">
          <span class="custom-tree-node" slot-scope="{ node, data }">
            <span>
              <span>
                <i class="el-icon-search"></i>
              </span>
              <span style="margin: 0px 10px;">{{ node.label }}</span>
              <span style="color: #b5b5b5; font-size: 13px">
                <el-tag type="success" size="mini" effect="plain">
                  菜单
                </el-tag>
              </span>
            </span>
            <span style="float: right">
              <button type="button" class="el-button el-button--text el-button--mini">
                <span>
                  <i class="el-icon-edit"></i>编辑
                </span>
              </button>
              <button type="button" class="el-button el-button--text el-button--mini">
                <span>
                  <i class="el-icon-edit"></i>编辑
                </span>
              </button>
              <button type="button" class="el-button el-button--text el-button--mini">
                <span>
                  <i class="el-icon-edit"></i>编辑
                </span>
              </button>
            </span>
          </span>
        </el-tree>
      </el-card>
    </div>
</template>

<script>
    export default {
      name: "Menus",
      watch: {
        filterText(val) {
          this.$refs.tree.filter(val);
        }
      },
      methods: {
        filterNode(value, data) {
          if (!value) return true;
          return data.label.indexOf(value) !== -1;
        }
      },
      data() {
        return {
          filterText: '',
          data: [{
            id: 1,
            label: '一级 1',
            children: [{
              id: 4,
              label: '二级 1-1',
              children: [{
                id: 9,
                label: '三级 1-1-1'
              }, {
                id: 10,
                label: '三级 1-1-2'
              }]
            }]
          }, {
            id: 2,
            label: '一级 2',
            children: [{
              id: 5,
              label: '二级 2-1'
            }, {
              id: 6,
              label: '二级 2-2'
            }]
          }, {
            id: 3,
            label: '一级 3',
            children: [{
              id: 7,
              label: '二级 3-1'
            }, {
              id: 8,
              label: '二级 3-2'
            }]
          }],
          defaultProps: {
            children: 'children',
            label: 'label'
          }
        };
      }
    }
</script>

<style scoped lang="less">
  .menu-container{
    margin: 0;
    padding: 0;
    height: 100%;
    width: 100%;
  }

</style>
