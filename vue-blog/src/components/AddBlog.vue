<template>
    <div class="blog" v-theme="'narrow'">
      <h1>添加博客</h1>
      <form v-if="!isSubmitted">
        <p>
          <label for="title">标题：</label>
          <input type="text" name="title" id="title" v-model="blog.title"/>
        </p>
        <p>
          <label for="content">内容：</label>
          <textarea name="content" id="content" v-model="blog.content"></textarea>
        </p>
        <p>
          <label>分类：</label>
          电影
          <input type="checkbox" name="sort" value="电影" v-model="blog.sorts"/>
          游戏
          <input type="checkbox" name="sort" value="游戏" v-model="blog.sorts"/>
          娱乐
          <input type="checkbox" name="sort" value="娱乐" v-model="blog.sorts"/>
        </p>
        <p>
          <label for="author">作者：</label>
          <select id="author" name="author" v-model="blog.author">
            <option value="apple">apple</option>
            <option value="rose">rose</option>
            <option value="jack">jack</option>
          </select>
        </p>
        <p><button v-on:click.prevent="submit">发布博客</button></p>
      </form>
      <div class="suc" v-if="isSubmitted">已发布成功</div>
      <div class="info">
        <div>标题：{{blog.title}}</div>
        <div>内容：{{blog.content}}</div>
        <div>分类：
          <ul>
            <li v-for="sort in blog.sorts">
              {{sort}}
            </li>
          </ul>
        </div>
        <div>作者：{{blog.author}}</div>
      </div>
    </div>
</template>

<script>
    export default {
      name: "AddBlog",
      data(){
        return{
          blog:{
            title: '',
            content: '',
            sorts: [],
            author: ''
          },
          isSubmitted: false
        }
      },
      methods: {
       submit() {
         // this.$http.post(
         //     "https://jsonplaceholder.typicode.com/posts",
         //  {
         //    title: this.blog.title,
         //    body: this.blog.content,
         //    userId: 1
         //  }
         // ).then(function (data) {
         //   console.log(data);
         //   this.isSubmitted = true;
         // })
         this.$axios.post(
           "/posts",
           {
             title: this.blog.title,
             body: this.blog.content,
             userId: 1
           }
         ).then(data => {
           this.isSubmitted = true;
         })
       }
      }
    }
</script>

<style scoped>
  .blog *{
    box-sizing: border-box;
  }
  .blog{
    margin: 10px auto;
    max-width: 600px;
    padding: 20px;
    background: #eeeeee;
  }
  h1{
    text-align: center;
  }
  label{
    display: block;
    padding: 10px 0px;
  }
  input[type="text"],select,textarea{
    display: block;
    width: 100%;
    padding: 10px;
  }
  textarea{
    height: 200px;
  }
  button{
    padding: 10px;
    color: lightslategrey;
    background: aliceblue;
    border-radius: 8px;
    cursor: pointer;
  }
  .info{
    border: 1px dotted #ccc;
    padding: 10px 20px;
    margin: 30px 0;
  }
  .suc{
    text-align: center;
  }
</style>
