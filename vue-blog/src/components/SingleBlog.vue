<template>
    <div class="single-blog" v-theme="'narrow'">
      <router-link to="/">返回</router-link>
      <h3 v-rainbow>{{blog.title}}</h3>
      <article>
        {{blog.body}}
      </article>
    </div>
</template>

<script>
    export default {
      name: "SingleBlog",
      data(){
        return {
          id: this.$route.params.id,
          blog: {}
        }
      },
      created() {
        // this.$http.get("https://jsonplaceholder.typicode.com/posts/" + this.id).then(function (data) {
        //   this.blog = data.body;
        // })
        this.$axios.get("/posts/" + this.id).then(data => {
          this.blog = data.data;
        })
      }
    }
</script>

<style scoped>
  .single-blog{
    margin: 0 auto;
    background: beige;
    padding: 20px;
  }
  a{
    text-decoration: none;
    background: dodgerblue;
    padding: 10px;
    margin: 120px 0;
  }
</style>
