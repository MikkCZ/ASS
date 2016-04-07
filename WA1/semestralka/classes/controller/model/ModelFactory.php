<?php

namespace controller\model;

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
                $model = new \model\Index();
                break;
            case 'signup':
                $model = new \model\Signup();
                break;
            case 'login':
                $model = new \model\Login();
                break;
            case 'user':
                $model = new \model\User();
                break;
            case 'gallery':
                $model = new \model\Gallery();
                break;
            default:
                $model = new \model\Error404();
        }
        return $model;
    }

}
