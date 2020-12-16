<template>
    <nav v-theme="'narrow'">
      <ul>
        <li>
          <router-link to="/" exact>博客列表</router-link>
        </li>
        <li>
          <router-link to="/AddBlog" exact>发布博客</router-link>
        </li>
      </ul>
    </nav>
</template>

<script>
    export default {
      name: "BlogHeader",
    }
</script>

<style scoped>
  nav{
    background: cornflowerblue;
    padding: 20px;
    margin: 10px auto;
  }
  a{
    text-decoration: none;
    border-radius: 5px;
    color: #fff;
    padding: 20px;
  }
  ul{
    text-align: center;
  }
  li{
    display: inline-block;
    font-size: 18px;
    margin: 0 10px;
  }
  .router-link-active{
    background: rgba(255,255,255, 0.4);
  }
</style>
