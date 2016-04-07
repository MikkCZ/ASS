<?php

namespace controller\view;

/**
 * ViewFactory is a static glass to get the appopriate view implementation for the request represeted by model.
 *
 * @author Michal Stanke <michal.stanke@mikk.cz>
 */
class ViewFactory {

    /**
     * Returns the view implementation appropriate for the given model.
     * 
     * @param \model\ModelClass $model model implementation for the request
     * @return \view\ViewClass view class implementation
     */
    public static function getView(\model\ModelClass $model) {
        $class_name = get_class($model);
        $class_name = 'view\\' . substr($class_name, strpos($class_name, '\\') + 1);
        return new $class_name($model);
    }

}
