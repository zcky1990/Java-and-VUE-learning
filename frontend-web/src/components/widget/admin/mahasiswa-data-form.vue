<template>
  <div class="pendaftaran-form">
    <div class="sign-up-container">
      <div class="form-container">
        <v-form ref="form" v-model="valid" width="300">
          <div class="personal-data-container">
            <div class="user-data-section">
              <div class="title-sections">Data Personal</div>
            </div>
            <v-row>
              <v-col cols="12" md="6">
                <v-text-field
                  v-model="data.idNumber"
                  :rules="idNumberRules"
                  label="Nomor Identitas"
                  required
                  color="#00d1b2"
                ></v-text-field>
              </v-col>
              <v-col cols="12" md="6">
                <v-text-field
                  v-model="data.fullName"
                  :rules="nameRules"
                  label="Nama Lengkap"
                  required
                  color="#00d1b2"
                ></v-text-field>
              </v-col>

              <v-col cols="12" md="6">
                <v-text-field
                  v-model="data.email"
                  :rules="emailRules"
                  label="E-mail"
                  required
                  color="#00d1b2"
                ></v-text-field>
              </v-col>
              <v-col cols="12" md="6">
                <v-text-field
                  v-model="data.phoneNumber"
                  :rules="phoneNumberRules"
                  label="Nomor Telpon"
                  required
                  color="#00d1b2"
                ></v-text-field>
              </v-col>

              <v-col cols="12" md="12">
                <v-textarea
                  v-model="data.address"
                  name="input-7-1"
                  label="Alamat"
                  value
                  hint="masukan alamat anda"
                ></v-textarea>
              </v-col>
              <v-col cols="12" md="4">
                <v-select v-model="data.religion" :items="itemsReligion" required label="Agama"></v-select>
              </v-col>
              <v-col cols="12" md="4">
                <v-select v-model="data.gender" :items="itemsGender" required label="Jenis Kelamin"></v-select>
              </v-col>
              <v-col cols="12" md="4">
                <v-select v-model="data.status" :items="itemsStatus" required label="Status"></v-select>
              </v-col>
              <v-col cols="12" md="6">
                <v-text-field
                  v-model="data.placeOfBirth"
                  :rules="placeOfBirthRules"
                  label="Tempat lahir"
                  required
                  color="#00d1b2"
                ></v-text-field>
              </v-col>
              <v-col cols="12" md="6">
                <v-menu
                  ref="startMenu"
                  v-model="startMenu"
                  :close-on-content-click="false"
                  :nudge-right="40"
                  :return-value.sync="data.birthday"
                  transition="scale-transition"
                  min-width="290px"
                  offset-y
                  full-width
                >
                  <template v-slot:activator="{ on }">
                    <v-text-field
                      v-model="data.birthday"
                      label="Tanggal Lahir"
                      prepend-icon="event"
                      readonly
                      v-on="on"
                    ></v-text-field>
                  </template>
                  <v-date-picker v-model="data.birthday" no-title scrollable>
                    <v-spacer></v-spacer>
                    <v-btn text color="primary" @click="startMenu = false">Cancel</v-btn>
                    <v-btn text color="primary" @click="$refs.startMenu.save(data.birthday)">OK</v-btn>
                  </v-date-picker>
                </v-menu>
              </v-col>
              <v-col cols="12" md="3">
                <v-text-field
                  v-model="data.city"
                  :rules="cityRules"
                  label="Kota"
                  required
                  color="#00d1b2"
                ></v-text-field>
              </v-col>
              <v-col cols="12" md="3">
                <v-text-field
                  v-model="data.subDistrict"
                  :rules="subDistrictRules"
                  label="Kelurahan"
                  required
                  color="#00d1b2"
                ></v-text-field>
              </v-col>
              <v-col cols="12" md="3">
                <v-text-field
                  v-model="data.district"
                  :rules="districtRules"
                  label="Kecamatan"
                  required
                  color="#00d1b2"
                ></v-text-field>
              </v-col>
              <v-col cols="12" md="3">
                <v-text-field
                  v-model="data.zipcode"
                  :rules="zipCodeRules"
                  label="Kode Pos"
                  required
                  color="#00d1b2"
                ></v-text-field>
              </v-col>
            </v-row>
          </div>

          <div class="data-pendidikan-sections">
            <div class="pendidikan-container">
              <div class="title-sections">Data Pendidikan</div>
            </div>
            <v-row>
              <v-col cols="12" md="12">
                <v-text-field
                  v-model="data.sekolah.schoolName"
                  :rules="schoolNameRules"
                  label="Nama Sekolah"
                  required
                  color="#00d1b2"
                ></v-text-field>
              </v-col>
              <v-col cols="12" md="12">
                <v-textarea
                  v-model="data.sekolah.schoolAddress"
                  :rules="schoolAddressRules"
                  name="input-7-1"
                  label="Alamat Sekolah"
                  value
                  hint="Masukan alamat sekolah anda"
                ></v-textarea>
              </v-col>
              <v-col cols="12" md="4">
                <v-select
                  v-model="data.sekolah.schoolType"
                  :items="itemsSchoolType"
                  required
                  label="Jenis Sekolah"
                ></v-select>
              </v-col>
              <v-col cols="12" md="4">
                <v-text-field
                  v-model="data.sekolah.schoolMajor"
                  :rules="schoolMajorRules"
                  label="Jurusan"
                  required
                  color="#00d1b2"
                ></v-text-field>
              </v-col>
              <v-col cols="12" md="4">
                <v-text-field
                  v-model="data.sekolah.graduationYear"
                  :rules="graduationYearRules"
                  label="Tahun lulus Sekolah"
                  required
                  color="#00d1b2"
                ></v-text-field>
              </v-col>
            </v-row>
          </div>
          <div class="prodi-sections">
            <div class="prodi-container">
              <div class="title-sections">Prodi Pilihan</div>
            </div>
            <v-select
              :items="itemsProdiList"
              v-model="data.prodi"
              label="Pilihan Program Studi"
              outlined
              item-text="prodiName"
              return-object
              color="rgb(0, 209, 178)"
            ></v-select>
          </div>
          <v-flex class="btn-container no-print">
            <div class="submit-btn-container">
              <div class="sign-in-btn-container">
                <div class="sign-in-btn" @click="submit">Edit</div>
              </div>
            </div>

            <div class="submit-btn-container">
              <div class="sign-in-btn-container">
                <div class="sign-in-btn" @click="Print">Print</div>
              </div>
            </div>
          </v-flex>
        </v-form>
      </div>
    </div>
  </div>
</template>

<script>
import { EventBus } from "./../../../EventBus.js";
import jsPDF from "jspdf";
import imageLogo from "@/assets/image/stiami.jpeg";

export default {
  name: "user-daftar-form",
  props: {
    data: {
      type: Object
    },
    list: {
      type: Array
    }
  },
  data() {
    return {
      imageData: imageLogo,
      valid: false,
      show1: false,
      startMenu: false,
      start: "2019-01-12",
      itemsGender: ["Laki-laki", "Perempuan"],
      itemsStatus: ["Menikah", "Belum Menikah"],
      itemsReligion: ["Islam", "Kristen", "Budha", "Hindu"],
      itemsSchoolType: ["SMA", "SMK", "STM"],
      nameRules: [v => !!v || "Nama lengkap harus diisi"],
      schoolNameRules: [v => !!v || "Nama Sekolah harus diisi"],
      schoolAddressRules: [v => !!v || "Alamat Sekolah harus diisi"],
      graduationYearRules: [v => !!v || "Tahun Lulus harus diisi"],
      schoolMajorRules: [v => !!v || "Jurusan Sekolah harus diisi"],
      cityRules: [v => !!v || "Nama Kota harus diisi"],
      subDistrictRules: [v => !!v || "Nama Kelurahan harus diisi"],
      districtRules: [v => !!v || "Nama Kecamatan harus diisi"],
      phoneNumberRules: [v => !!v || "Nomor telpon harus diisi"],
      zipCodeRules: [v => !!v || "Kode Pos harus diisi"],
      cityRules: [v => !!v || "Nama Kota harus diisi"],
      statusRules: [v => !!v || "Status harus di pilih"],
      idNumberRules: [v => !!v || "Nomor identitas harus diisi"],
      placeOfBirthRules: [v => !!v || "Tempat lahir harus diisi"],
      emailRules: [
        v => !!v || "E-mail harus diisi",
        v => /.+@.+/.test(v) || "E-mail harus valid"
      ],
      addressRules: [v => !!v || "Alamat harus diisi"],
      passwordRules: [v => !!v || "Password is required"],
      itemsProdiList: [],
      isLoading: false
    };
  },
  created() {
    this.itemsProdiList = this.list;
  },
  methods: {
    toDataUrl(url, callback) {
      var xhr = new XMLHttpRequest();
      xhr.onload = function() {
        var reader = new FileReader();
        reader.onloadend = function() {
          callback(reader.result);
        };
        reader.readAsDataURL(xhr.response);
      };
      xhr.open("GET", url);
      xhr.responseType = "blob";
      xhr.send();
    },

    Print: function() {
      var doc = new jsPDF();
      //header form
      doc.setFontStyle("bold");
      doc.setFontSize("12");
      doc.text("STIE Dharma Agung Bandung", 60, 20);

      doc.setFontSize("10");
      doc.text("Jl industri no 33 Cikarang kota", 60, 25);

      doc.setFontSize("10");
      doc.text("Cikarang Utara kab Bekasi 17530", 60, 30);

      doc.setFontSize("10");
      doc.text("081997111818", 60, 35);

      //row title section 1
      doc.setFontStyle("bold");
      doc.setFontSize("14");
      doc.text("Data Personal", 20, 50);
      doc.line(20, 52, 180, 52);
      //row 1
      doc.setFontStyle("normal");
      doc.setFontSize("8");
      doc.setTextColor(150);
      doc.text("Nomor Identitas", 20, 60);

      doc.setFontSize("8");
      doc.setTextColor(150);
      doc.text("Nama Lengkap", 100, 60);

      doc.setFontSize("12");
      doc.setTextColor("black");
      doc.text(this.data.idNumber, 20, 65);

      doc.setFontSize("12");
      doc.setTextColor("black");
      doc.text(this.data.fullName, 100, 65);
      //row 2
      doc.setFontSize("8");
      doc.setTextColor(150);
      doc.text("Email", 20, 75);

      doc.setFontSize("8");
      doc.setTextColor(150);
      doc.text("Nomor Telpon", 100, 75);

      doc.setFontSize("12");
      doc.setTextColor("black");
      doc.text(this.data.email, 20, 80);

      doc.setFontSize("12");
      doc.setTextColor("black");
      doc.text(this.data.phoneNumber, 100, 80);
      //row 3
      doc.setFontSize("8");
      doc.setTextColor(150);
      doc.text("Alamat", 20, 90);

      doc.setFontSize("12");
      doc.setTextColor("black");
      doc.text(this.data.address, 20, 95);

      //row 4
      doc.setFontSize("8");
      doc.setTextColor(150);
      doc.text("Agama", 20, 110);

      doc.setFontSize("8");
      doc.setTextColor(150);
      doc.text("Jenis Kelamin", 60, 110);

      doc.setFontSize("8");
      doc.setTextColor(150);
      doc.text("Status", 100, 110);

      doc.setFontSize("12");
      doc.setTextColor("black");
      doc.text(this.data.religion, 20, 115);

      doc.setFontSize("12");
      doc.setTextColor("black");
      doc.text(this.data.gender, 60, 115);

      doc.setFontSize("12");
      doc.setTextColor("black");
      doc.text(this.data.status, 100, 115);

      //row 5
      doc.setFontSize("8");
      doc.setTextColor(150);
      doc.text("Tempat Lahir", 20, 120);

      doc.setFontSize("8");
      doc.setTextColor(150);
      doc.text("Tanggal Lahir", 100, 120);

      doc.setFontSize("12");
      doc.setTextColor("black");
      doc.text(this.data.placeOfBirth, 20, 125);

      doc.setFontSize("12");
      doc.setTextColor("black");
      doc.text(this.data.birthday, 100, 125);

      //row 6
      doc.setFontSize("8");
      doc.setTextColor(150);
      doc.text("Kota", 20, 135);

      doc.setFontSize("8");
      doc.setTextColor(150);
      doc.text("Kelurahan", 60, 135);

      doc.setFontSize("8");
      doc.setTextColor(150);
      doc.text("Kecamatan", 100, 135);

      doc.setFontSize("8");
      doc.setTextColor(150);
      doc.text("Kodepos", 140, 135);

      doc.setFontSize("12");
      doc.setTextColor("black");
      doc.text(this.data.city, 20, 140);

      doc.setFontSize("12");
      doc.setTextColor("black");
      doc.text(this.data.subDistrict, 60, 140);

      doc.setFontSize("12");
      doc.setTextColor("black");
      doc.text(this.data.district, 100, 140);

      doc.setFontSize("12");
      doc.setTextColor("black");
      doc.text(this.data.zipcode, 140, 140);

      //row title section 2
      doc.setFontStyle("bold");
      doc.setFontSize("14");
      doc.text("Data Pendidikan", 20, 150);
      doc.line(20, 152, 180, 152);

      //row 7
      doc.setFontStyle("normal");
      doc.setFontSize("8");
      doc.setTextColor(150);
      doc.text("Nama Sekolah", 20, 160);

      doc.setFontSize("12");
      doc.setTextColor("black");
      doc.text(this.data.sekolah.schoolName, 20, 165);

      //row 8
      doc.setFontSize("8");
      doc.setTextColor(150);
      doc.text("Alamat Sekolah", 20, 175);

      doc.setFontSize("12");
      doc.setTextColor("black");
      doc.text(this.data.sekolah.schoolAddress, 20, 180);
      //row 9
      doc.setFontSize("8");
      doc.setTextColor(150);
      doc.text("Jenis Sekolah", 20, 195);

      doc.setFontSize("8");
      doc.setTextColor(150);
      doc.text("Jurusan", 60, 195);

      doc.setFontSize("8");
      doc.setTextColor(150);
      doc.text("Tahun Lulus Sekolah", 100, 195);

      doc.setFontSize("12");
      doc.setTextColor("black");
      doc.text(this.data.sekolah.schoolType, 20, 200);

      doc.setFontSize("12");
      doc.setTextColor("black");
      doc.text(this.data.sekolah.schoolMajor, 60, 200);

      doc.setFontSize("12");
      doc.setTextColor("black");
      doc.text(this.data.sekolah.graduationYear, 100, 200);

      //row title section 3
      doc.setFontStyle("bold");
      doc.setFontSize("14");
      doc.text("Prodi Pilihan", 20, 215);
      doc.line(20, 217, 180, 217);

      //row 10
      doc.setFontStyle("normal");
      doc.setFontSize("8");
      doc.setTextColor(150);
      doc.text("Prodi pilihan", 20, 225);

      doc.setFontSize("12");
      doc.setTextColor("black");
      doc.text(this.data.prodi.prodiName, 20, 230);

      let name = this.data.fullName;
      let user = name.split(" ").join("-");
      doc.save("form-pendaftaran-" + user + ".pdf");
    },
    callRestService(model) {
      let self = this;
      this.isLoading = true;
      let router = this.$router;
      let headers = this.getDefaultHeaders(this.getMeta("token"));
      this.put(
        "/mahasiswa/edit",
        model,
        headers,
        function(response) {
          if (response.status == 200) {
            let responseData = response.data;
            if (responseData["error_message"] != undefined) {
              self.setMessage(responseData.error_message, 1);
              self.isLoading = false;
            } else {
              self.isLoading = false;
            }
          }
        },
        function(e) {
          self.isLoading = false;
          self.setMessage(e, 1);
        }
      );
    },
    setMessage(message, type) {
      let messageData = {};
      messageData.message = message;
      messageData.type = type;
      EventBus.$emit("SNACKBAR_TRIGGERED", messageData);
    },
    submit() {
      if (this.$refs.form.validate()) {
        this.callRestService(this.data);
      }
    },
    goToPage() {
      this.$router.push("/");
    }
  },
  watch: {
    data: function() {
      console.log(this.data);
    }
  }
};
</script>

<style scoped>
@media only screen and (max-width: 600px) {
  .sign-up-container {
    flex-direction: column !important;
  }

  .title {
    font-size: 1.4rem !important;
    font-weight: 600;
    color: rgb(0, 209, 178);
    text-align: center;
  }
}

.title-container {
  flex-grow: 1;
  text-align: center;
  margin-bottom: 16px;
}

.form-container {
  flex-grow: 0;
  margin-left: 2%;
  margin-right: 2%;
}

.sign-up-container {
  flex-direction: row;
}

.submit-btn-container {
  display: flex;
  flex-direction: row;
}

.link-not-sign-up {
  flex-grow: 1;
  z-index: 1;
}

.sign-up-link {
  text-decoration: none;
}

.title {
  text-align: center;
  line-height: 1.5 !important;
  font-size: 1.5rem !important;
  color: rgb(0, 209, 178);
  font-weight: 900;
}

.sub-title {
  text-align: center;
  color: #6c757d;
  font-size: 1rem;
  font-weight: 300;
}

.bulma-color {
  color: rgb(0, 209, 178);
}

.submit-btn-container {
  display: flex;
  flex-direction: row;
  margin-right: 20px;
}

.btn-container {
  display: flex;
  flex-direction: row;
}

.link-not-sign-up {
  flex-grow: 1;
  color: #00d1b2;
}

.sign-up-link {
  text-decoration: none;
}

.link-btn {
  color: #00d1b2;
  font-size: 1rem;
  font-weight: 400;
  line-height: 1.5;
  cursor: pointer;
}

.left-side-container {
  margin-bottom: 20px;
  width: 300px;
  margin-left: 2%;
  margin-right: 2%;
}

.title-sections {
  font-size: 1.5em;
  line-height: 2;
  background: #00d1b2;
  padding: 5px;
  color: white;
}

.prodi-container {
  margin-bottom: 20px;
}

.title-sub-title-container {
  width: fit-content;
  margin: 0 auto;
}

.desc {
  font-size: 1.25rem;
  font-weight: 600;
}

.facebook-btn,
.google-btn {
  margin-bottom: 10px;
}

.term-condition-container {
  text-align: center;
  max-width: 600px;
  margin: 0 auto;
  margin-top: 5%;
  line-height: 24px;
  color: #858f96;
}

.sign-in-btn {
  padding: 8px;
  width: 110px;
  border: 1px solid;
  text-align: center;
  border-radius: 15px;
  border: 1px solid rgb(0, 209, 178);
  color: rgb(0, 209, 178);
  cursor: pointer;
}

.sign-in-btn:hover {
  background: #00d1b2;
  border: 1px solid rgb(0, 209, 178);
  color: white;
}
</style>
