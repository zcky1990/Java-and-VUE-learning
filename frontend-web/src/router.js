import Vue from 'vue'
import Router from 'vue-router'
import Index from '@/pages/users/index'
import Article from '@/pages/users/article'
import Pages from '@/pages/users/pages'
import Pendaftaran from '@/pages/users/pendaftaran'
import Kontak from '@/pages/users/kontak'
import VisiMisi from '@/pages/users/visi-misi'
import Sejarah from '@/pages/users/sejarah'

import LoginAdmin from '@/pages/admin/login'
import AdminPages from '@/pages/admin/pages-admin'
import AdminMenu from '@/pages/admin/menu-admin'
import ArticleAdmin from '@/pages/admin/article'
import AdminHero from '@/pages/admin/hero'
import AdminFaculty from '@/pages/admin/faculty-admin'
import AdminProdi from '@/pages/admin/prodi-admin'

// import AdminSejarah from '@/pages/admin/sejarah-admin'
// import AdminVisiMisi from '@/pages/admin/visi-misi-admin'
import Admin from '@/pages/admin/admin'
import Category from '@/pages/admin/category'
import AccessLevel from '@/pages/admin/access-list'
import Roles from '@/pages/admin/roles'
import UsersAdmin from '@/pages/admin/users'

Vue.use(Router)
export default new Router({
  mode:"history",
  routes: [
    {
      path: '/',
      name: 'Index',
      component: Index
    },
    {
      path: '/kontak',
      name: 'Kontak',
      component: Kontak
    },
    {
      path: '/pendaftaran',
      name: 'Pendaftaran',
      component: Pendaftaran
    },
    {
      path: '/visi-misi',
      name: 'VisiMisi',
      component: VisiMisi
    },
    {
      path: '/sejarah',
      name: 'Sejarah',
      component: Sejarah
    },
    {
      path: '/news/:slug',
      name: 'News',
      component: Article
    },
    {
      path: '/pages/:slug',
      name: 'Pages',
      component: Pages,
    },
    {
      path: '/admin/login',
      name: 'AdminLogin',
      component: LoginAdmin
    },
    {
      path: '/admin',
      name: 'Admin',
      component: Admin
    },
    {
      path: '/admin/hero',
      name: 'AdminHero',
      component: AdminHero
    },
    {
      path: '/admin/faculty',
      name: 'AdminFaculty',
      component: AdminFaculty
    },
    {
      path: '/admin/prodi',
      name: 'AdminProdi',
      component: AdminProdi
    },
    {
      path: '/admin/pages',
      name: 'AdminPages',
      component: AdminPages
    },
    {
      path: '/admin/menu',
      name: 'AdminMenu',
      component: AdminMenu
    },
    // {
    //   path: '/admin/sejarah',
    //   name: 'AdminSejarah',
    //   component: AdminSejarah
    // },
    // {
    //   path: '/admin/visi-misi',
    //   name: 'AdminVisiMisi',
    //   component: AdminVisiMisi
    // },
    {
      path: '/admin/news',
      name: 'AdminNews',
      component: ArticleAdmin
    },
    {
      path: '/admin/category',
      name: 'AdminCategory',
      component: Category
    },
    {
      path: '/admin/access_level',
      name: 'AdminAccessLevel',
      component: AccessLevel
    },
    {
      path: '/admin/roles',
      name: 'AdminRoles',
      component: Roles
    },
    {
      path: '/admin/users',
      name: 'AdminUsers',
      component: UsersAdmin
    },
  ]
})
