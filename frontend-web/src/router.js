import Vue from 'vue'
import Router from 'vue-router'
import Index from '@/pages/users/index'
import Article from '@/pages/users/article'
import Pendaftaran from '@/pages/users/pendaftaran'
import Kontak from '@/pages/users/kontak'
import VisiMisi from '@/pages/users/visi-misi'
import Sejarah from '@/pages/users/sejarah'

import LoginAdmin from '@/pages/admin/login'
import ArticleAdmin from '@/pages/admin/article'
import AdminSejarah from '@/pages/admin/sejarah-admin'
import AdminVisiMisi from '@/pages/admin/visi-misi-admin'
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
      path: '/admin/sejarah',
      name: 'AdminSejarah',
      component: AdminSejarah
    },
    {
      path: '/admin/visi-misi',
      name: 'AdminVisiMisi',
      component: AdminVisiMisi
    },
    {
      path: '/admin/news',
      name: 'News',
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
