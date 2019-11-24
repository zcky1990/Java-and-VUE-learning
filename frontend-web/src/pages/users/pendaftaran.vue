<template>
  <pendaftaran-layout-page></pendaftaran-layout-page>
</template>

<script>
import PendaftaranLayout from "@/layout/users/pendaftaran-layout";

export default {
  name: "login",
  components: {
    "pendaftaran-layout-page": PendaftaranLayout
  }
};
</script>