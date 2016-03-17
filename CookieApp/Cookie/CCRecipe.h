//
//  CCRecipe.h
//  Cookie
//
//  Created by Developer on 16/3/17.
//  Copyright © 2016年 Developer. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface CCRecipe : NSObject

@property(nonatomic, retain) NSString *name;
@property(nonatomic, retain) NSString *desc;
@property(nonatomic, retain) NSString *topImgUrl;

-(id) initWithDict:(NSDictionary *)dict;

@end
