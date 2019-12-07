    <template>
  <div class="admin-page">
    <mahasiswa-layout></mahasiswa-layout>
  </div>
</template>

    <script>
//import Navbar from "@/components/widget/Navbar";
import MahasiswaLayout from "@/layout/admin/mahasiswa-layout";

export default {
  name: "admin-mahasiswa-page",
  components: {
    "mahasiswa-layout": MahasiswaLayout
  },
  created() {
    this.checkAdminSession(this.$session, this.$router);
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
