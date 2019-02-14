import { FuseNavigation } from '@fuse/types';

export const navigation: FuseNavigation[] = [
    {
        id: 'applications',
        title: 'Applications',
        translate: 'Меню',
        type: 'group',
        children: [
            {
                id: 'home',
                title: 'Начало',
                translate: 'Начало',
                type: 'item',
                icon: 'home',
                url: '/home'
            },
            {
                id: 'new-questionnaire',
                title: 'Нов въпросник',
                translate: 'Нов въпросник',
                type: 'item',
                icon: 'create',
                url: '/new-questionnaire'
            },
            {
                id: 'history',
                title: 'Лично досие',
                translate: 'Лично досие',
                type: 'item',
                icon: 'history',
                url: '/history'
            },
            {
                id: 'profile',
                title: 'Потребителски профил',
                translate: 'Потребителски профил',
                type: 'item',
                icon: 'person',
                url: '/profile'
            },
            {
                id: 'status-check',
                title: 'Проверка на статус',
                translate: 'Проверка на статус',
                type: 'item',
                icon: 'beenhere',
                url: '/status-check'
            },
            {
                id: 'users',
                title: 'Потребители',
                translate: 'Потребители',
                type: 'collapsable',
                icon: 'supervised_user_circle',
                children: [
                    {
                        id: 'create_users',
                        title: 'Администриране КРС потребител',
                        type: 'item',
                        url: '/user/crc-user-administration'
                    },
                    {
                        id: 'user_administration',
                        title: 'Администриране на оператор',
                        type: 'item',
                        url: '/user/operator-administration'
                    },
                    {
                        id: 'company_operator_admin',
                        title: 'Админ Предприятие',
                        type: 'item',
                        url: '/admin/company-operator-admin'
                    }
                ]
            }

        ]
    }
];
