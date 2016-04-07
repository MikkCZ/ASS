<?php

namespace admin\controller\model;

/**
 * ModelFactory is a static glass to get the appopriate model implementation for the request.
 *
 * @author Michal Stanke <michal.stanke@mikk.cz>
 */
class ModelFactory {

    /**
     * Returns the model implementation appropriate for the parsed request.
     * 
     * @param string $relative_request_url first part of the request URL relative to the application root
     * @return \model\ModelClass model class implementation
     */
    public static function getModel($relative_request_url) {
        switch ($relative_request_url) {
            case '': case 'index': case 'index.htm': case 'index.html': case 'index.php':
                $model = new \admin\model\Index();
                break;
            case 'login':
                $model = new \admin\model\Login();
                break;
            case 'user':
                $model = new \admin\model\User();
                break;
            case 'users':
                $model = new \admin\model\Users();
                break;
            case 'gallery':
                $model = new \admin\model\Gallery();
                break;
            case 'settings':
                $model = new \admin\model\Settings();
                break;
            default:
                $model = new \admin\model\Error404();
        }
        return $model;
    }

}
