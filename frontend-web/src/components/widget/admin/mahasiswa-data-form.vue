<template>
<div class="pendaftaran-form">
    <div class="sign-up-container" align-center justify-center flex>
        <div class="form-container">
            <v-form ref="form" v-model="valid" width="300">
                <v-text-field v-model="data.idNumber" :rules="idNumberRules" label="Nomor Identitas" required color="#00d1b2"></v-text-field>
                <v-text-field v-model="data.fullName" :rules="nameRules" label="Name Lengkap" required color="#00d1b2"></v-text-field>
                <v-textarea v-model="data.address" name="input-7-1" label="Alamat" value hint="masukan alamat anda"></v-textarea>
                <v-select v-model="data.religion" :items="itemsReligion" required label="Agama"></v-select>
                <v-text-field v-model="data.email" :rules="emailRules" label="E-mail" required color="#00d1b2"></v-text-field>
                <v-select v-model="data.gender" :items="itemsGender" required label="Jenis Kelamin"></v-select>
                <v-select v-model="data.status" :items="itemsStatus" required label="Status"></v-select>
                <div class="birthday">
                    <v-text-field v-model="data.placeOfBirth" :rules="placeOfBirthRules" label="Tempat lahir" required color="#00d1b2"></v-text-field>
                    <v-menu ref="startMenu" v-model="startMenu" :close-on-content-click="false" :nudge-right="40" :return-value.sync="data.birthday" transition="scale-transition" min-width="290px">
                        <template v-slot:activator="{ on }">
                            <v-text-field v-model="data.birthday" label="Tanggal Lahir" prepend-icon="event" readonly v-on="on"></v-text-field>
                        </template>
                        <v-date-picker v-model="data.birthday" no-title scrollable>
                            <v-spacer></v-spacer>
                            <v-btn text color="primary" @click="startMenu = false">
                                Cancel
                            </v-btn>
                            <v-btn text color="primary" @click="$refs.startMenu.save(data.birthday)">
                                OK
                            </v-btn>
                        </v-date-picker>
                    </v-menu>
                </div>

                <v-text-field v-model="data.city" :rules="cityRules" label="Kota" required color="#00d1b2"></v-text-field>
                <v-text-field v-model="data.subDistrict" :rules="subDistrictRules" label="Kelurahan" required color="#00d1b2"></v-text-field>
                <v-text-field v-model="data.district" :rules="districtRules" label="Kecamatan" required color="#00d1b2"></v-text-field>
                <v-text-field v-model="data.zipcode" :rules="zipCodeRules" label="Kode Pos" required color="#00d1b2"></v-text-field>
                <v-text-field v-model="data.phoneNumber" :rules="phoneNumberRules" label="Nomor Telpon" required color="#00d1b2"></v-text-field>

                <div class="data-pendidikan-sections">
                    <v-text-field v-model="data.sekolah.schoolName" :rules="schoolNameRules" label="Nama Sekolah" required color="#00d1b2"></v-text-field>
                    <v-textarea v-model="data.sekolah.schoolAddress" :rules="schoolAddressRules" name="input-7-1" label="Alamat Sekolah" value hint="Masukan alamat sekolah anda"></v-textarea>
                    <v-select v-model="data.sekolah.schoolType" :items="itemsSchoolType" required label="Jenis Sekolah"></v-select>
                    <v-text-field v-model="data.sekolah.schoolMajor" :rules="schoolMajorRules" label="Jurusan" required color="#00d1b2"></v-text-field>
                    <v-text-field v-model="data.sekolah.graduationYear" :rules="graduationYearRules" label="Tahun lulus Sekolah" required color="#00d1b2"></v-text-field>
                </div>
                <v-select :items="list" v-model="data.prodi" label="Pilihan Program Studi" item-text="prodiName" return-object color="rgb(0, 209, 178)"></v-select>
                <v-flex align-center justify-center>
                    <div class="submit-btn-container">
                        <div class="sign-in-btn-container">
                            <div class="sign-in-btn" @click="submit">Edit</div>
                        </div>
                    </div>
                </v-flex>
            </v-form>
        </div>
    </div>
</div>
</template>

<script>
import {
    EventBus
} from "./../../../EventBus.js";

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
            valid: false,
            show1: false,
            startMenu: false,
            start: '2019-01-12',
            itemsGender: ["Laki-laki", "Perempuan"],
            itemsStatus: ["Menikah", "Belum Menikah"],
            itemsReligion: ["Islam", "Kristen", "Budha", "Hindu"],
            itemsSchoolType: ["SMA", "SMK", "STM"],
            nameRules: [
                v => !!v || "Nama lengkap harus diisi"
                //v => v.length <= 10 || 'Name must be less than 10 characters'
            ],
            schoolNameRules: [
                v => !!v || "Nama Sekolah harus diisi"
                //v => v.length <= 10 || 'Name must be less than 10 characters'
            ],
            schoolAddressRules: [
                v => !!v || "Alamat Sekolah harus diisi"
                //v => v.length <= 10 || 'Name must be less than 10 characters'
            ],
            graduationYearRules: [
                v => !!v || "Tahun Lulus harus diisi"
                //v => v.length <= 10 || 'Name must be less than 10 characters'
            ],
            schoolMajorRules: [
                v => !!v || "Jurusan Sekolah harus diisi"
                //v => v.length <= 10 || 'Name must be less than 10 characters'
            ],
            cityRules: [
                v => !!v || "Nama Kota harus diisi"
                //v => v.length <= 10 || 'Name must be less than 10 characters'
            ],
            subDistrictRules: [
                v => !!v || "Nama Kelurahan harus diisi"
                //v => v.length <= 10 || 'Name must be less than 10 characters'
            ],
            districtRules: [
                v => !!v || "Nama Kecamatan harus diisi"
                //v => v.length <= 10 || 'Name must be less than 10 characters'
            ],
            phoneNumberRules: [
                v => !!v || "Nomor telpon harus diisi"
                //v => v.length <= 10 || 'Name must be less than 10 characters'
            ],
            zipCodeRules: [
                v => !!v || "Kode Pos harus diisi"
                //v => v.length <= 10 || 'Name must be less than 10 characters'
            ],
            cityRules: [
                v => !!v || "Nama Kota harus diisi"
                //v => v.length <= 10 || 'Name must be less than 10 characters'
            ],
            statusRules: [
                v => !!v || "Status harus di pilih"
                //v => v.length <= 10 || 'Name must be less than 10 characters'
            ],
            idNumberRules: [
                v => !!v || "Nomor identitas harus diisi"
                //v => v.length <= 10 || 'Name must be less than 10 characters'
            ],
            placeOfBirthRules: [
                v => !!v || "Tempat lahir harus diisi"
                //v => v.length <= 10 || 'Name must be less than 10 characters'
            ],
            emailRules: [
                v => !!v || "E-mail harus diisi",
                v => /.+@.+/.test(v) || "E-mail harus valid"
            ],
            addressRules: [
                v => !!v || "Alamat harus diisi",
            ],
            passwordRules: [
                v => !!v || "Password is required",
            ],
            isLoading: false
        };
    },
    created() {},
    methods: {
        callRestService(model) {
            let self = this;
            this.isLoading = true;
            let router = this.$router;
            let headers = this.getDefaultHeaders(this.getMeta("token"));
            this.put(
                "/mahasiswa/edit",
                model,
                headers,
                function (response) {
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
                function (e) {
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
        data: function () {
            console.log(this.data)
        }
    },
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
    width: 320px;
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
