    <template>
  <div class="visi-misi-page-container">
    <section class="nav-section">
      <nav-bar></nav-bar>
    </section>
    <section class="content-section">
      <visi-misi></visi-misi>
    </section>
  </div>
</template>
    <script>
import NavbarComponents from "@/components/widget/navbar";
import VisiMisiComponents from "@/components/widget/visi-misi-components";

export default {
  name: "visi-misi-page-layout",
  components: {
    "nav-bar": NavbarComponents,
    "visi-misi" : VisiMisiComponents
    }
};
</script>

<style scoped>

</style>
