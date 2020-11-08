<template>
  <div>
    <el-dialog v-bind="$attrs" v-on="$listeners" @open="onOpen" @close="onClose" :title="title" :visible.sync="showDialog" v-if="showDialog">
      <el-form ref="elForm" :model="menu" :rules="rules" size="medium" label-width="100px">
        <el-form-item label="节点名称" prop="menuName">
          <el-input v-model="menu.menuName" placeholder="请输入节点名称" clearable :style="{width: '100%'}">
          </el-input>
        </el-form-item>
        <el-form-item label="URL" prop="url">
          <el-input v-model="menu.url" placeholder="请输入URL,例如:/home" clearable :style="{width: '100%'}"></el-input>
        </el-form-item>
        <el-form-item label="权限编码" prop="perms">
          <el-input v-model="menu.perms" placeholder="请输入权限编码,例如:user:add" clearable :style="{width: '100%'}">
          </el-input>
        </el-form-item>
        <el-form-item label="图标" prop="icon">
          <el-select v-model="menu.icon" placeholder="请选中图标" :style="{width: '100%'}" clearable filterable>
            <el-option
              v-for="item in icons"
              :key="item.value"
              :label="item.value"
              :value="item.value">
              <span style="float: left;"><i :class="item.value"></i></span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{ item.value }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否可用" prop="available">
          <el-radio-group v-model="menu.available" size="medium">
            <el-radio v-for="(item, index) in availableOptions" :key="index" :label="item.value"
              :disabled="item.disabled">{{item.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="是否展开" prop="open">
          <el-radio-group v-model="menu.open" size="medium">
            <el-radio v-for="(item, index) in openOptions" :key="index" :label="item.value"
              :disabled="item.disabled">{{item.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-radio-group v-model="menu.type" size="medium">
            <el-radio v-for="(item, index) in typeOptions" :key="index" :label="item.value"
              :disabled="item.disabled">{{item.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="排序" prop="orderNum">
          <el-input-number v-model="menu.orderNum" placeholder="排序" :step='1' :min='1' :max='10'>
          </el-input-number>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="onClose">取消</el-button>
        <el-button type="primary" @click="handelConfirm">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import {getMenuById, saveOrUpdate} from "../../api/menuApi";

  export default {
  name: 'AddMenu',
  inheritAttrs: false,
  components: {},
  props: {
    addOrUpdateVisible:{
      type: Boolean,
      default: false
    },
    id:{
      type: Number,
      default: 0
    },
    flag:{
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      // 控制弹出框显示隐藏
      showDialog:false,
      title: '',
      menu: {},
      rules: {
        menuName: [{
          required: true,
          message: '请输入节点名称',
          trigger: 'blur'
        }],
        url: [],
        perms: [],
        icon: [],
        available: [{
          required: true,
          message: '是否可用不能为空',
          trigger: 'change'
        }],
        open: [{
          required: true,
          message: '是否展开不能为空',
          trigger: 'change'
        }],
        type: [{
          required: true,
          message: '类型不能为空',
          trigger: 'change'
        }],
        orderNum: [{
          required: true,
          message: '排序不能为空',
          trigger: 'blur'
        }],
      },
      availableOptions: [{
        "label": "不可用",
        "value": 0
      }, {
        "label": "可用",
        "value": 1
      }],
      openOptions: [{
        "label": "不展开",
        "value": 0
      }, {
        "label": "展开",
        "value": 1
      }],
      typeOptions: [{
        "label": "菜单",
        "value": "0"
      }, {
        "label": "按钮",
        "value": "1"
      }],
      icons: [
        {
          value: 'el-icon-platform-eleme'
        },
        {
          value: 'el-icon-eleme'
        },
        {
          value: 'el-icon-delete-solid'
        },
        {
          value: 'el-icon-delete'
        },
        {
          value: 'el-icon-s-tools'
        },
        {
          value: 'el-icon-setting'
        },
        {
          value: 'el-icon-user-solid'
        },
        {
          value: 'el-icon-user'
        },
        {
          value: 'el-icon-phone'
        },
        {
          value: 'el-icon-phone-outline'
        },
        {
          value: 'el-icon-more'
        },
        {
          value: 'el-icon-more-outline'
        },
        {
          value: 'el-icon-star-on'
        },
        {
          value: 'el-icon-star-off'
        },
        {
          value: 'el-icon-s-goods'
        },
        {
          value: 'el-icon-goods'
        },
        {
          value: 'el-icon-warning'
        },
        {
          value: 'el-icon-warning-outline'
        },
        {
          value: 'el-icon-question'
        },
        {
          value: 'el-icon-info'
        },
        {
          value: 'el-icon-remove'
        },
        {
          value: 'el-icon-circle-plus'
        },
        {
          value: 'el-icon-success'
        },
        {
          value: 'el-icon-error'
        },
        {
          value: 'el-icon-zoom-in'
        },
        {
          value: 'el-icon-zoom-out'
        },
        {
          value: 'el-icon-remove-outline'
        },
        {
          value: 'el-icon-circle-plus-outline'
        },
        {
          value: 'el-icon-circle-check'
        },
        {
          value: 'el-icon-circle-close'
        },
        {
          value: 'el-icon-s-help'
        },
        {
          value: 'el-icon-help'
        },
        {
          value: 'el-icon-minus'
        },
        {
          value: 'el-icon-plus'
        },
        {
          value: 'el-icon-check'
        },
        {
          value: 'el-icon-close'
        },
        {
          value: 'el-icon-picture'
        },
        {
          value: 'el-icon-picture-outline'
        },
        {
          value: 'el-icon-picture-outline-round'
        },
        {
          value: 'el-icon-upload'
        },
        {
          value: 'el-icon-upload2'
        },
        {
          value: 'el-icon-download'
        },
        {
          value: 'el-icon-camera-solid'
        },
        {
          value: 'el-icon-camera'
        },
        {
          value: 'el-icon-video-camera-solid'
        },
        {
          value: 'el-icon-video-camera'
        },
        {
          value: 'el-icon-message-solid'
        },
        {
          value: 'el-icon-bell'
        },
        {
          value: 'el-icon-s-cooperation'
        },
        {
          value: 'el-icon-s-order'
        },
        {
          value: 'el-icon-s-platform'
        },
        {
          value: 'el-icon-s-fold'
        },
        {
          value: 'el-icon-s-unfold'
        },
        {
          value: 'el-icon-s-operation'
        },
        {
          value: 'el-icon-s-promotion'
        },
        {
          value: 'el-icon-s-home'
        },
        {
          value: 'el-icon-s-release'
        },
        {
          value: 'el-icon-s-ticket'
        },
        {
          value: 'el-icon-s-management'
        },
        {
          value: 'el-icon-s-open'
        },
        {
          value: 'el-icon-s-shop'
        },
        {
          value: 'el-icon-s-marketing'
        },
        {
          value: 'el-icon-s-flag'
        },
        {
          value: 'el-icon-s-comment'
        },
        {
          value: 'el-icon-s-finance'
        },
        {
          value: 'el-icon-s-claim'
        },
        {
          value: 'el-icon-s-custom'
        },
        {
          value: 'el-icon-s-opportunity'
        },
        {
          value: 'el-icon-s-data'
        },
        {
          value: 'el-icon-s-check'
        },
        {
          value: 'el-icon-s-grid'
        },
        {
          value: 'el-icon-menu'
        },
        {
          value: 'el-icon-share'
        },
        {
          value: 'el-icon-d-caret'
        },
        {
          value: 'el-icon-caret-left'
        },
        {
          value: 'el-icon-caret-right'
        },
        {
          value: 'el-icon-caret-bottom'
        },
        {
          value: 'el-icon-caret-top'
        },
        {
          value: 'el-icon-bottom-left'
        },
        {
          value: 'el-icon-bottom-right'
        },
        {
          value: 'el-icon-back'
        },
        {
          value: 'el-icon-right'
        },
        {
          value: 'el-icon-bottom'
        },
        {
          value: 'el-icon-top'
        },
        {
          value: 'el-icon-top-left'
        },
        {
          value: 'el-icon-top-right'
        },
        {
          value: 'el-icon-arrow-left'
        },
        {
          value: 'el-icon-arrow-right'
        },
        {
          value: 'el-icon-arrow-down'
        },
        {
          value: 'el-icon-arrow-up'
        },
        {
          value: 'el-icon-d-arrow-left'
        },
        {
          value: 'el-icon-d-arrow-right'
        },
        {
          value: 'el-icon-video-pause'
        },
        {
          value: 'el-icon-video-play'
        },
        {
          value: 'el-icon-refresh'
        },
        {
          value: 'el-icon-refresh-right'
        },
        {
          value: 'el-icon-refresh-left'
        },
        {
          value: 'el-icon-finished'
        },
        {
          value: 'el-icon-sort'
        },
        {
          value: 'el-icon-sort-up'
        },
        {
          value: 'el-icon-sort-down'
        },
        {
          value: 'el-icon-rank'
        },
        {
          value: 'el-icon-loading'
        },
        {
          value: 'el-icon-view'
        },
        {
          value: 'el-icon-c-scale-to-original'
        },
        {
          value: 'el-icon-date'
        },
        {
          value: 'el-icon-edit'
        },
        {
          value: 'el-icon-edit-outline'
        },
        {
          value: 'el-icon-folder'
        },
        {
          value: 'el-icon-folder-opened'
        },
        {
          value: 'el-icon-folder-add'
        },
        {
          value: 'el-icon-folder-remove'
        },
        {
          value: 'el-icon-folder-delete'
        },
        {
          value: 'el-icon-folder-checked'
        },
        {
          value: 'el-icon-tickets'
        },
        {
          value: 'el-icon-document-remove'
        },
        {
          value: 'el-icon-document-delete'
        },
        {
          value: 'el-icon-document-copy'
        },
        {
          value: 'el-icon-document-checked'
        },
        {
          value: 'el-icon-document'
        },
        {
          value: 'el-icon-document-add'
        },
        {
          value: 'el-icon-printer'
        },
        {
          value: 'el-icon-paperclip'
        },
        {
          value: 'el-icon-takeaway-box'
        },
        {
          value: 'el-icon-search'
        },
        {
          value: 'el-icon-monitor'
        },
        {
          value: 'el-icon-attract'
        },
        {
          value: 'el-icon-mobile'
        },
        {
          value: 'el-icon-scissors'
        },
        {
          value: 'el-icon-umbrella'
        },
        {
          value: 'el-icon-headset'
        },
        {
          value: 'el-icon-brush'
        },
        {
          value: 'el-icon-mouse'
        },
        {
          value: 'el-icon-coordinate'
        },
        {
          value: 'el-icon-magic-stick'
        },
        {
          value: 'el-icon-reading'
        },
        {
          value: 'el-icon-data-line'
        },
        {
          value: 'el-icon-data-board'
        },
        {
          value: 'el-icon-pie-chart'
        },
        {
          value: 'el-icon-data-analysis'
        },
        {
          value: 'el-icon-collection-tag'
        },
        {
          value: 'el-icon-film'
        },
        {
          value: 'el-icon-suitcase'
        },
        {
          value: 'el-icon-suitcase-1'
        },
        {
          value: 'el-icon-receiving'
        },
        {
          value: 'el-icon-collection'
        },
        {
          value: 'el-icon-files'
        },
        {
          value: 'el-icon-notebook-1'
        },
        {
          value: 'el-icon-notebook-2'
        },
        {
          value: 'el-icon-toilet-paper'
        },
        {
          value: 'el-icon-office-building'
        },
        {
          value: 'el-icon-school'
        },
        {
          value: 'el-icon-table-lamp'
        },
        {
          value: 'el-icon-house'
        },
        {
          value: 'el-icon-no-smoking'
        },
        {
          value: 'el-icon-smoking'
        },
        {
          value: 'el-icon-shopping-cart-full'
        },
        {
          value: 'el-icon-shopping-cart-1'
        },
        {
          value: 'el-icon-shopping-cart-2'
        },
        {
          value: 'el-icon-shopping-bag-1'
        },
        {
          value: 'el-icon-shopping-bag-2'
        },
        {
          value: 'el-icon-sold-out'
        },
        {
          value: 'el-icon-sell'
        },
        {
          value: 'el-icon-present'
        },
        {
          value: 'el-icon-box'
        },
        {
          value: 'el-icon-bank-card'
        },
        {
          value: 'el-icon-money'
        },
        {
          value: 'el-icon-coin'
        },
        {
          value: 'el-icon-wallet'
        },
        {
          value: 'el-icon-discount'
        },
        {
          value: 'el-icon-price-tag'
        },
        {
          value: 'el-icon-news'
        },
        {
          value: 'el-icon-guide'
        },
        {
          value: 'el-icon-male'
        },
        {
          value: 'el-icon-female'
        },
        {
          value: 'el-icon-thumb'
        },
        {
          value: 'el-icon-cpu'
        },
        {
          value: 'el-icon-link'
        },
        {
          value: 'el-icon-connection'
        },
        {
          value: 'el-icon-open'
        },
        {
          value: 'el-icon-turn-off'
        },
        {
          value: 'el-icon-set-up'
        },
        {
          value: 'el-icon-chat-round'
        },
        {
          value: 'el-icon-chat-line-round'
        },
        {
          value: 'el-icon-chat-square'
        },
        {
          value: 'el-icon-chat-dot-round'
        },
        {
          value: 'el-icon-chat-dot-square'
        },
        {
          value: 'el-icon-chat-line-square'
        },
        {
          value: 'el-icon-message'
        },
        {
          value: 'el-icon-postcard'
        },
        {
          value: 'el-icon-position'
        },
        {
          value: 'el-icon-turn-off-microphone'
        },
        {
          value: 'el-icon-microphone'
        },
        {
          value: 'el-icon-close-notification'
        },
        {
          value: 'el-icon-bangzhu'
        },
        {
          value: 'el-icon-time'
        },
        {
          value: 'el-icon-odometer'
        },
        {
          value: 'el-icon-crop'
        },
        {
          value: 'el-icon-aim'
        },
        {
          value: 'el-icon-switch-button'
        },
        {
          value: 'el-icon-full-screen'
        },
        {
          value: 'el-icon-copy-document'
        },
        {
          value: 'el-icon-mic'
        },
        {
          value: 'el-icon-stopwatch'
        },
        {
          value: 'el-icon-medal-1'
        },
        {
          value: 'el-icon-medal'
        },
        {
          value: 'el-icon-trophy'
        },
        {
          value: 'el-icon-trophy-1'
        },
        {
          value: 'el-icon-first-aid-kit'
        },
        {
          value: 'el-icon-discover'
        },
        {
          value: 'el-icon-place'
        },
        {
          value: 'el-icon-location'
        },
        {
          value: 'el-icon-location-outline'
        },
        {
          value: 'el-icon-location-information'
        },
        {
          value: 'el-icon-add-location'
        },
        {
          value: 'el-icon-delete-location'
        },
        {
          value: 'el-icon-map-location'
        },
        {
          value: 'el-icon-alarm-clock'
        },
        {
          value: 'el-icon-timer'
        },
        {
          value: 'el-icon-watch-1'
        },
        {
          value: 'el-icon-watch'
        },
        {
          value: 'el-icon-lock'
        },
        {
          value: 'el-icon-unlock'
        },
        {
          value: 'el-icon-key'
        },
        {
          value: 'el-icon-service'
        },
        {
          value: 'el-icon-mobile-phone'
        },
        {
          value: 'el-icon-bicycle'
        },
        {
          value: 'el-icon-truck'
        },
        {
          value: 'el-icon-ship'
        },
        {
          value: 'el-icon-basketball'
        },
        {
          value: 'el-icon-football'
        },
        {
          value: 'el-icon-soccer'
        },
        {
          value: 'el-icon-baseball'
        },
        {
          value: 'el-icon-wind-power'
        },
        {
          value: 'el-icon-light-rain'
        },
        {
          value: 'el-icon-lightning'
        },
        {
          value: 'el-icon-heavy-rain'
        },
        {
          value: 'el-icon-sunrise'
        },
        {
          value: 'el-icon-sunrise-1'
        },
        {
          value: 'el-icon-sunset'
        },
        {
          value: 'el-icon-sunny'
        },
        {
          value: 'el-icon-cloudy'
        },
        {
          value: 'el-icon-partly-cloudy'
        },
        {
          value: 'el-icon-cloudy-and-sunny'
        },
        {
          value: 'el-icon-moon'
        },
        {
          value: 'el-icon-moon-night'
        },
        {
          value: 'el-icon-dish'
        },
        {
          value: 'el-icon-dish-1'
        },
        {
          value: 'el-icon-food'
        },
        {
          value: 'el-icon-chicken'
        },
        {
          value: 'el-icon-fork-spoon'
        },
        {
          value: 'el-icon-knife-fork'
        },
        {
          value: 'el-icon-burger'
        },
        {
          value: 'el-icon-tableware'
        },
        {
          value: 'el-icon-sugar'
        },
        {
          value: 'el-icon-dessert'
        },
        {
          value: 'el-icon-ice-cream'
        },
        {
          value: 'el-icon-hot-water'
        },
        {
          value: 'el-icon-water-cup'
        },
        {
          value: 'el-icon-coffee-cup'
        },
        {
          value: 'el-icon-cold-drink'
        },
        {
          value: 'el-icon-goblet'
        },
        {
          value: 'el-icon-goblet-full'
        },
        {
          value: 'el-icon-goblet-square'
        },
        {
          value: 'el-icon-goblet-square-full'
        },
        {
          value: 'el-icon-refrigerator'
        },
        {
          value: 'el-icon-grape'
        },
        {
          value: 'el-icon-watermelon'
        },
        {
          value: 'el-icon-cherry'
        },
        {
          value: 'el-icon-apple'
        },
        {
          value: 'el-icon-pear'
        },
        {
          value: 'el-icon-orange'
        },
        {
          value: 'el-icon-coffee'
        },
        {
          value: 'el-icon-ice-tea'
        },
        {
          value: 'el-icon-ice-drink'
        },
        {
          value: 'el-icon-milk-tea'
        },
        {
          value: 'el-icon-potato-strips'
        },
        {
          value: 'el-icon-lollipop'
        },
        {
          value: 'el-icon-ice-cream-square'
        },
        {
          value: 'el-icon-ice-cream-round'
        }
      ],
    }
  },
  computed: {},
  watch: {
    // 监听 addOrUpdateVisible 改变
    addOrUpdateVisible(oldVal,newVal){
      this.showDialog = this.addOrUpdateVisible;
      //监听到 addOrUpdateVisible === True 和 id > 0 时查询用户
      if(this.addOrUpdateVisible){
        if(this.id > 0){
          this.getMenuById(this.flag);
        }else{
          this.title = "添加第一父级";
          this.menu.parentId = 0;
        }
      }
    },
  },
  created() {},
  mounted() {},
  methods: {
    onOpen() {
    },
    onClose() {
      this.$refs['elForm'].resetFields();
      this.menu = {};
      // 子组件调用父组件方法，并传递参数
      this.$emit('changeShow','false');
    },
    handelConfirm() {
      this.$refs['elForm'].validate(valid => {
        if (!valid) return;
        this.saveOrUpdate();
      })
    },
    //新增修改
    async saveOrUpdate(){
      this.menu.flag = this.flag;
      const {data} = await saveOrUpdate(this.menu);
      if(data.status){
        this.$message.success(data.message);
        this.$refs['elForm'].resetFields();
        this.menu = {};
        // 子组件调用父组件方法，并传递参数
        this.$emit('changeShow','false');
        this.$parent.menuButtonTree();
      }else{
        this.$message.error(data.message);
      }
    },
    //根据ID查询菜单信息
    async getMenuById(flag){
      const {data} = await getMenuById(this.id);
      if(data.status){
        if(this.flag){
          this.menu.parentId = data.data.menu.id;
          this.title = "添加节点：【" + data.data.menu.menuName + "】";
        }else{
          this.menu = data.data.menu;
          this.title = "修改节点：【" + data.data.menu.menuName + "】";
        }
      }else{
        this.$message.error(data.message);
      }
    }
  }
}

</script>
<style>
</style>
