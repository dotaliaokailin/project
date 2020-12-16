<template>
    <div v-theme:color="theme" class="blog">
      <h1>博客列表</h1>
      <input name="search" id="search" v-model="search" placeholder="搜索"/>
      <div class="content" v-for="blog in filteredBlogs" :key="blog.id">
        <router-link v-bind:to="'/blog/' + blog.id">
          <h3 v-rainbow>{{blog.title | toUpperCase}}</h3>
        </router-link>
        <article>
          {{blog.body | snippet}} <!-- | 管道符，配置filter-->
        </article>
      </div>
    </div>
</template>

<script>
    export default {
      name: "ShowBlog",
      data(){
        return{
          blogs:[],
          search: '',
          theme: 'narrow' //wide
        }
      },
      created() {
        //https://jsonplaceholder.typicode.com/posts
        // this.$http.get("./../static/posts.json").then(function (data) {
        //   this.blogs = data.body.slice(0, 10);
        // })
        this.$axios.get("/posts").then(data => {
          this.blogs = data.data;
        })
      },
      computed:{//用户计算博客列表标题与搜索框匹配，如果没匹配返回全部
        filteredBlogs() {
          return this.blogs.filter((blog) => {
            return blog.title.match(this.search);
          });
        }
      },
      filters:{
        toUpperCase(value){
          return value.toUpperCase();
        },
        snippet(value){
          return value.slice(0, 100) + '...';
        }
      },
      // directives:{//钩子函数
      //   'rainbow': {
      //     bind(el, binding, vnode){
      //       el.style.color = "#" + Math.random().toString(16).slice(2, 8);
      //     }
      //   }
      // }
    }
</script>

<style scoped>
  .blog *{
    box-sizing: border-box;
  }
  .blog{
    margin: 0 auto;
  }
  h1{
    text-align: center;
  }
  .content{
    border: 1px solid #eeeeee;
    margin: 10px auto;
    padding: 20px;
    background: #cccccc;
  }
  input{
    width: 100%;
    padding: 10px;
  }
  a{
    text-decoration: none;
  }
</style>
