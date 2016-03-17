//
//  CCRecipe.m
//  Cookie
//
//  Created by Developer on 16/3/17.
//  Copyright © 2016年 Developer. All rights reserved.
//

#import "CCRecipe.h"

@implementation CCRecipe

-(id) initWithDict:(NSDictionary *)dict {
    self = [super init];
    if (self) {
        self.name = dict[@"name"];
        self.topImgUrl = dict[@"localTopImgPath"];
        self.desc = dict[@"description"];
        
    }
    return self;

}

@end
