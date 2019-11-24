<template>
  <div class="article-page-container">
    <login-admin></login-admin>
  </div>
</template>

<script>
import loginFormAdmin from "@/components/widget/admin/admin-login-form";

export default {
  name: "admin-login-page-layout",
  components: {
   "login-admin" : loginFormAdmin
  },
  created() {
  }
};
</script>
<style scoped>
.article-page-container {
    height: 100vh;
    display: flex;
}
</style>
